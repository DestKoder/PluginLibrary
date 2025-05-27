package ru.dest.library.velocity;

import com.velocitypowered.api.command.CommandMeta;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import ru.dest.library.command.BaseCommand;
import ru.dest.library.listener.PluginListener;
import ru.dest.library.plugin.IRegistry;
import ru.dest.library.plugin.MinecraftPlugin;

public class VelocityRegistry<T extends MinecraftPlugin<T, ?>> implements IRegistry<T> {

    private final T plugin;

    public VelocityRegistry(T plugin) {
        this.plugin = plugin;
    }

    @SafeVarargs
    @Override
    public final void register(BaseCommand<T> @NotNull ... commands) {
        for(BaseCommand<T> cmd : commands){
            CommandMeta cmdMeta = plugin.getServer().getCommandManager().metaBuilder(cmd.getName())
                    .aliases(cmd.getAliases().toArray(new String[0]))
                    .plugin(plugin)
                    .build();
            plugin.getServer().getCommandManager().register(cmdMeta, new CommandWrap(cmd));
        }
    }

    @SafeVarargs
    @Override
    public final void register(PluginListener<T> @NotNull ... listeners) {
        for(PluginListener<T> listener: listeners){
            plugin.getServer().getEventManager().register(plugin, listener);
        }
    }

    @Override
    public void registerListeners(String packageName) {
        new Reflections(packageName).getSubTypesOf(PluginListener.class).forEach(cl -> {
            try {
                register(cl.getDeclaredConstructor(plugin.getClass()).newInstance(plugin));
            } catch (Exception e) {return;}
        });
    }

    @Override
    public void registerCommands(String packageName) {
        new Reflections(packageName).getSubTypesOf(BaseCommand.class).forEach(cl -> {
            try {
                register(cl.getDeclaredConstructor(plugin.getClass()).newInstance(plugin));
            } catch (Exception e) {return;}
        });
    }
}
