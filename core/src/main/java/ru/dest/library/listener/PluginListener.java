package ru.dest.library.listener;


import ru.dest.library.plugin.IPlugin;

/**
 * Describe a listener which belongs to some Library plugin
 * @param <PL>
 */
public abstract class PluginListener<PL extends IPlugin> {

    protected final PL plugin;

    public PluginListener(PL plugin) {
        this.plugin = plugin;
    }
}
