package ru.dest.library.listener;

import org.bukkit.event.Listener;
import ru.dest.library.plugin.IPlugin;

public abstract class BukkitListener<T extends IPlugin<?>> extends PluginListener<T> implements Listener {

    public BukkitListener(T plugin) {
        super(plugin);
    }

}
