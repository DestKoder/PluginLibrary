package ru.dest.library.listener;


import ru.dest.library.plugin.IPlugin;

public abstract class PluginListener<PL extends IPlugin> {

    protected final PL plugin;

    public PluginListener(PL plugin) {
        this.plugin = plugin;
    }
}
