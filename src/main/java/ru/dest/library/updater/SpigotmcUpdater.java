package ru.dest.library.updater;


import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class SpigotmcUpdater {

    private final JavaPlugin plugin;
    private final int resourceId;

    public SpigotmcUpdater(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    @Nullable
    private String g() {
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNext()) {
                return scanner.next();
            }else return null;
        } catch (IOException exception) {
            plugin.getLogger().info("Unable to check for updates: " + exception.getMessage());
            return null;
        }
    }

    public boolean hasUpdates(){
        String v = g();

        if(v == null) {
            plugin.getLogger().info("Update check failed.");
            return false;
        }

        return !v.equals(plugin.getDescription().getVersion());
    }
}
