package ru.dest.library.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import ru.dest.library.Library;
import ru.dest.library.exception.UnsupportedFileTypeException;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Map;

@UtilityClass
public final class ConfigWorker {
    private static final Type mapType = new TypeToken<Map<String, Object>>() { }.getType();
//    private static Gson;

    public @NotNull DataConfig load(@NotNull File f) throws IOException{
        if(f.getName().endsWith(".yml") || f.getName().endsWith(".yaml")){
            return loadYaml(f);
        } else if (f.getName().endsWith(".json")) {
            return loadJson(f);
        }
        throw new UnsupportedFileTypeException(f.getName().split("\\.")[1]);
    }

    public @NotNull DataConfig loadRemote(@NotNull String url) throws Exception {
        if(url.endsWith(".json")){
            return loadJsonRemote(url);
        }else if(url.endsWith(".yaml") || url.endsWith(".yml")){
            return loadYamlRemote(url);
        }
        throw new UnsupportedFileTypeException(url.split("\\.")[1]);
    }

    @Contract("_ -> new")
    private @NotNull DataConfig loadYamlRemote(String url) throws Exception{
        URL remoteAddress = new URL(url);

        InputStream stream = remoteAddress.openStream();
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(stream);
        stream.close();

        return new DataConfig(data);
    }

    @Contract("_ -> new")
    private @NotNull DataConfig loadJsonRemote(String url) throws Exception {
        URL remoteAddress = new URL(url);

        InputStream stream = remoteAddress.openStream();
        InputStreamReader reader = new InputStreamReader(stream);
        Gson gson = Library.builder.create();

        Map<String, Object> data = gson.fromJson(reader, mapType);
        reader.close();
        stream.close();

        return new DataConfig(data);
    }

    public void save(@NotNull File f, DataConfig config) throws IOException{
        if(!f.exists()){
            FileUtils.createParentDirectories(f);
            f.createNewFile();
        }
        if(f.getName().endsWith(".yml") || f.getName().endsWith(".yaml")){
             saveYaml(f, config);
        } else if (f.getName().endsWith(".json")) {
             saveJson(f, config);
        }
    }

    private void saveYaml(File f, @NotNull DataConfig config) throws IOException{
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndicatorIndent(2);
        options.setIndentWithIndicator(true);
        Yaml yaml = new Yaml(options);
        FileWriter writer = new FileWriter(f);
        yaml.dump(config.getData(), writer);
        writer.close();
    }

    private void saveJson(File f, @NotNull DataConfig config) throws IOException{
        Gson gson = Library.builder.create();
        FileWriter writer = new FileWriter(f);
        gson.toJson(config.getData(), mapType, writer);
        writer.close();
    }

    @Contract("_ -> new")
    private @NotNull DataConfig loadYaml(File f) throws IOException {
        Yaml yaml = new Yaml();
        FileInputStream reader = new FileInputStream(f);
        Map<String, Object> data = yaml.load(reader);
        reader.close();
        return new DataConfig(data);
    }

    @Contract("_ -> new")
    private @NotNull DataConfig loadJson(File f) throws IOException {
        Gson gson = Library.builder.create();
        FileReader reader = new FileReader(f);
        Map<String,Object> data = gson.fromJson(reader, mapType);
        reader.close();
        return new DataConfig(data);
    }

}

