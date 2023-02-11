package ru.dest.library.gui;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class GUIContainer {

    private final Map<String, GUITemplate> data = new HashMap<>();
    private final File folder;

    public GUIContainer(@NotNull File folder) throws FileNotFoundException {
        this.folder = folder;

        if(!folder.exists() || folder.isFile()) throw new FileNotFoundException("Couldn't find folder with path: " + folder);

        this.reloadGuis();
    }

    public void reloadGuis() {
        data.clear();
        this.loadGuis(folder);
    }

    private void loadGuis(@NotNull File folder){
        for(File f : folder.listFiles()){
            if(f.isDirectory()){
                loadGuis(f);
            }else {
                if(!f.getName().endsWith(".yml")) continue;
                if(folder.getName().equals(this.folder.getName())){
                    this.data.put(f.getName().split("\\.")[0], GUITemplate.fromConfig(YamlConfiguration.loadConfiguration(f)));
                }else {
                    this.data.put(folder.getName()+"."+f.getName().split("\\.")[0], GUITemplate.fromConfig(YamlConfiguration.loadConfiguration(f)));
                }
            }
        }
    }

    @Nullable
    public GUITemplate getGUITemplate(String name){
        return data.getOrDefault(name, null);
    }
}
