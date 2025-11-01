package ru.dest.library.plugin;

import org.reflections.Reflections;
import ru.dest.library.command.BaseCommand;
import ru.dest.library.listener.PluginListener;

/**
 * Interface which represents a class which works
 * with command & listener registration
 * @param <T>
 */
public interface IRegistry<T extends IPlugin<?>> {

    void register(BaseCommand<T>... commands);
    void register(PluginListener<T>... listeners);

    void registerListeners(String packageName);
    void registerCommands(String packageName);



}
