package ru.dest.library.gui;


import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.Library;
import ru.dest.library.plugin.MinecraftPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GuiContainer {

    private final File file;
    private final Map<String, GUITemplate> cache = new HashMap<>();
    private final MinecraftPlugin<?,?> plugin;

    public GuiContainer(@NotNull File file, @NotNull MinecraftPlugin<?,?> plugin, @NotNull String... guis) {
        this.file = file;
        this.plugin = plugin;
        if(!file.exists()) {
            try {
                FileUtils.createParentDirectories(file);
                file.mkdir();
            } catch (IOException e) {
                Library.get().getLogger().warning("Error creating a gui directory");
                return;
            }
        }

        for(String s : guis){
            File gui = new File(file, s);
            if(!gui.exists()){
                plugin.saveResource(file.getName() +"/" + s, true);
            }
        }
    }

    @Nullable
    public GUITemplate get(String s){
        if(!cache.containsKey(s)){
            tryLoad(s);
        }
        return cache.getOrDefault(s, null);
    }

    private void tryLoad(String s){
        try{
            File gui = new File(file, s + ".yml");
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(gui);
            cache.put(s, GUITemplate.load(configuration));
            return;
        }catch (Exception e){
            plugin.logger().warning("Error loading gui " + s + ":");
            e.printStackTrace();
            return;
        }
    }
}
