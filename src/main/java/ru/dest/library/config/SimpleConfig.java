package ru.dest.library.config;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SimpleConfig extends Config<Plugin>{

    public SimpleConfig(@NotNull Plugin plugin, String path) {
        super(plugin, path);
    }
}
