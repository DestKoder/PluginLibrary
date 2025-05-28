package ru.dest.library.bukkit;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.reflections.Reflections;
import ru.dest.library.command.BaseCommand;
import ru.dest.library.command.CommandRegistrar;
import ru.dest.library.listener.BukkitListener;
import ru.dest.library.plugin.IRegistry;
import ru.dest.library.plugin.MinecraftPlugin;
import ru.dest.library.listener.PluginListener;
import ru.dest.library.utils.ReflectionUtils;

import java.lang.reflect.Field;

public final class BukkitRegistry<T extends MinecraftPlugin<T, ?>> extends CommandRegistrar<T> implements IRegistry<T> {

    private final T plugin;
    private final CommandMap commandMap;

    public BukkitRegistry(T plugin) throws Exception{
        super(plugin);
        this.plugin = plugin;

        Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        this.commandMap = (CommandMap) ReflectionUtils.getValue(field, Bukkit.getServer());
    }

    @Override
    public void register(BaseCommand<T> cmd) {
        this.register(cmd);
    }

    @SafeVarargs
    @Override
    public final void register(BaseCommand<T>... commands) {
        for(BaseCommand<T> cmd : commands){
            commandMap.register(plugin.getName(), new CommandWrap<>(cmd));
        }
    }

    @SafeVarargs
    @Override
    public final void register(PluginListener<T>... listeners) {
        for(PluginListener<T> l : listeners){
            if(!(l instanceof BukkitListener)) throw new IllegalArgumentException("Listener must be a BukkitListener");
            plugin.getServer().getPluginManager().registerEvents(((BukkitListener<T>)l), plugin);
        }
    }

    @Override
    public void registerListeners(String packageName) {
        new Reflections(packageName).getSubTypesOf(BaseCommand.class).forEach(cl -> {
            try {
                register(cl.getDeclaredConstructor(plugin.getClass()).newInstance(plugin));
            } catch (Exception e) {return;}
        });
    }

    @Override
    public void registerCommands(String packageName) {
        new Reflections(packageName).getSubTypesOf(BukkitListener.class).forEach(cl -> {
            try {
                register(cl.getDeclaredConstructor(plugin.getClass()).newInstance(plugin));
            } catch (Exception e) {return;}
        });
    }
}
