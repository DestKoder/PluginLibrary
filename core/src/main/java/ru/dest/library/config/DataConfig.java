package ru.dest.library.config;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@SuppressWarnings("unchecked")
public final class DataConfig {

    private final Map<String, Object> data;

    public DataConfig(Map<String, Object> data) {
        this.data = data == null ? new HashMap<>() : data;
    }

    @Nullable
    public Object get(@NotNull String key){
        return get(key, null);
    }

    public Object get(@NotNull String key, Object def){
        Object val = null;

//        System.out.println(key);
        if(!key.contains(".")){
            return data.getOrDefault(key, def);
        };

        String[] tmp = key.split("\\.");
        for(int i = 0 ; i < tmp.length; i ++){
            if(i != tmp.length-1){
                if(val == null) val = data.get(tmp[i]);

                else val = section(val).get(tmp[i]);
            }else {
                if(val != null) val = section(val).get(tmp[i]);
                else val = data.get(tmp[i]);
            }
        }
        if(val == null){
            return def;
        }else return val;
    }

    private Map<String, Object> section(Object map){
        if(!(map instanceof Map)) throw new IllegalArgumentException("Not map");
        Map<Object, Object> m = (Map<Object, Object>) map;
        Map<String,Object> section = new ConcurrentHashMap<>();
        m.forEach((k,v )-> {
            if(k.getClass() == String.class) section.put((String)k, v);
            else section.put(k.toString(), v);
        });

        m = null;
        return section;
    }

    public void set(@NotNull String key, Object value){
        Map<String, Object> m = data;
        if(!key.contains(".")){
            m.put(key, value);
            return;
        }
        String[] tmp = key.split("\\.");
        for(int i = 0 ; i < tmp.length; i ++){
            if(i != tmp.length - 1){
                if(!m.containsKey(tmp[i])) m.put(tmp[i], new HashMap<>());
                m = (Map<String, Object>) m.get(tmp[i]);
            }
        }
        key = tmp[tmp.length-1];
        m.put(key, value);
    }

    public Set<String> getKeys(){
        return getKeys(true);
    }

    public Set<String> getKeys(boolean deep){
        return deep ? fillKeys(data,"", new HashSet<>()) : data.keySet();
    }

    @Contract("_, _, _ -> param3")
    private Set<String> fillKeys(@NotNull Map<String, Object> section, @NotNull String parent, @NotNull Set<String> set){
        for(String s : section.keySet()){
            Object o = section.get(s);
            String nk = parent.isEmpty() ? s : parent + "." + s;
            if(o instanceof Map) fillKeys(section(o), nk,  set);
            else set.add(nk);
        }

        return set;
    }

    @Override
    public String toString(){ return data.toString();}

}
