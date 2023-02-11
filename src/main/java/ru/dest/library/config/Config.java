package ru.dest.library.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class Config<T extends Plugin> extends YamlConfiguration {

    protected final T plugin;
    protected final File data;

    public Config(@NotNull T plugin, String path){
        this.plugin = plugin;
        data = new File(plugin.getDataFolder(), path);

        this.load();
    }

    public void checkFile() throws IOException {
        if(!data.exists()) {
            data.createNewFile();
        }
    }

    public final void load(){
        try {
            checkFile();
            this.load(data);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().warning(e.getMessage());
            e.printStackTrace();
        }
        this.afterLoad();
    }

    public void afterLoad(){}

    public final void save(){
        try {
            this.save(data);
        }catch (IOException e){
            plugin.getLogger().warning(e.getMessage());
            e.printStackTrace();
        }
    }

}
