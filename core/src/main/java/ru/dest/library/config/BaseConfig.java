package ru.dest.library.config;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.config.ann.ConfigPath;
import ru.dest.library.object.FieldType;
import ru.dest.library.utils.ReflectionUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class BaseConfig {

    private File cFile;
    private String url;

    public BaseConfig(File file) {
        this.cFile = file;
    }

    public BaseConfig(String url) {
        this.url = url;
    }


    public void load() throws Exception{
        if(cFile == null  && url == null) throw new Exception();

        if(cFile != null && !cFile.exists()) save();

//        System.out.println("Loading config " + (cFile != null ? cFile.getPath() : url));
        DataConfig data = cFile != null ? ConfigWorker.load(cFile) : ConfigWorker.loadRemote(url);

        List<ConfigField> fields = getFields();

        for(ConfigField f : fields){
            Field field = f.getField();
            FieldType type = f.getType();
            String path = f.getPath();
            Object val = data.get(path);

//            System.out.println("Setting value of Field " + field.getName() + " to " + val);

            if(val == null)continue;
            if(type == FieldType.INTEGER){
                if(val instanceof Double){
                    val = ((Double)val).intValue();
                }
                if(val instanceof Float){
                    val = ((Float)val).intValue();
                }
            }
            if(type == FieldType.FLOAT){
                if(val instanceof Double){
                    val = ((Double)val).floatValue();
                }
                if(val instanceof Integer){
                    val = ((Integer)val).floatValue();
                }
            }

            switch (type){
                case ENUM:
                    ReflectionUtils.setValue(field, this, field.getType().getMethod("valueOf", String.class).invoke(null, (String) val));
                    break;
                case DOUBLE:
                case LONG:
                case FLOAT:
                case STRING:
                case BOOLEAN:
                case INTEGER:
                case LIST:
                    ReflectionUtils.setValue(field, this, val);
                //TODO: add serialization support
            }
            f.getField().setAccessible(true);
//            System.out.println(f.getField().getName() + " value is " + f.getField().get(this));
        }


    }

    public void save() throws Exception{
//        Map<String, Object> result = new HashMap<>();
        DataConfig result = new DataConfig(new HashMap<>());
        List<ConfigField> fields = getFields();

//        System.out.println(fields);
        for(ConfigField f : fields){
            Field field = f.getField();
            FieldType type = f.getType();
            String path = f.getPath();
            Object val = ReflectionUtils.getValue(field, this);

            if (Objects.requireNonNull(type) == FieldType.ENUM) {
                String tmp = (String) val.getClass().getMethod("name").invoke(val);
//                    m.put(key, tmp);
                result.set(path, tmp);
            } else {//                    m.put(key, val);
                result.set(path, val);
            }
        }

        ConfigWorker.save(cFile, result);
    }

    private @NotNull List<ConfigField> getFields(){
        List<ConfigField> l = new ArrayList<>();
        for(Field f : getClass().getDeclaredFields()){
            if(f.getName().equals("cFile")) continue;
            l.add(new ConfigField(f));
        }
        return l;
    }

    @Getter
    private static class ConfigField{
//        private String name;
        private final String path;
        private final Field field;
        private final FieldType type;

        public ConfigField(@NotNull Field f){
            this.path = f.isAnnotationPresent(ConfigPath.class) ? f.getAnnotation(ConfigPath.class).value() : f.getName().replaceAll("[A-Z]", ".$0").toLowerCase();
            this.field = f;
            this.type = ReflectionUtils.getFieldType(f);
        }

        @Override
        public String toString() {
            return "ConfigField{" +
                    "path='" + path + '\'' +
                    ", field=" + field +
                    ", type=" + type +
                    '}';
        }
    }
}
