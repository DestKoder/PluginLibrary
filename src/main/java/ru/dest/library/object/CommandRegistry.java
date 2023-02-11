package ru.dest.library.object;

import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.command.ConfigCommand;
import ru.dest.library.command.RuntimeCommand;
import ru.dest.library.exception.CommandNotFoundException;
import ru.dest.library.exception.RegistratorInitException;

import java.lang.reflect.Field;

/**
 * This Class is designed to register various types of commands. <br>
 *
 * An object of this class has already been created for each BukkitPlugin, you can get it via BukkitPlugin.commandRegistry();
 * @param <T> - your main class
 *
 * @since 1.0
 * @author DestKoder
 */
public final class CommandRegistry<T extends JavaPlugin> {
    private JavaPlugin plugin;

    private SimpleCommandMap scm;

    public CommandRegistry(@NotNull final JavaPlugin plugin) throws RegistratorInitException {
        this.plugin = plugin;

        SimplePluginManager spm = (SimplePluginManager) plugin.getServer().getPluginManager();
        Field f;
        try {
            f = SimplePluginManager.class.getDeclaredField("commandMap");

            f.setAccessible(true);

            this.scm = (SimpleCommandMap) f.get(spm);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RegistratorInitException("Couldn't setup command map.", e);
        }
    }

    /**
     * Method for registering runtime commands
     * @param cmd {@link RuntimeCommand} to register
     */
    public void registerCommand(@NotNull final RuntimeCommand<T> cmd){
        scm.register(plugin.getName(), cmd);
    }

    /**
     * Method for registering several {@link RuntimeCommand} in one call
     * @param commands list of commands to register
     */
    @SafeVarargs
    public final void registerCommands(RuntimeCommand<T> @NotNull ... commands){
        for (RuntimeCommand<T> cmd : commands) {
            this.registerCommand(cmd);
        }
    }


    /**
     * Method for registering plugin.yml-command
     * @param command {@link ConfigCommand} command to register.
     */
    public final void registerCommand(@NotNull final ConfigCommand<T> command){
        PluginCommand cmd = plugin.getCommand(command.getAliases().get(0));

        if(cmd == null) {
            throw new CommandNotFoundException(command.getAliases().get(0));
        }

        cmd.setExecutor(command);
        if(command instanceof TabCompleter) cmd.setTabCompleter((TabCompleter) command);
    }

    /**
     * Method for registering several plugin.yml-commands
     * @param commands - list of {@link ConfigCommand} to register;
     */
    @SafeVarargs
    public final void registerCommands(ConfigCommand<T>... commands){
        if(commands == null) return;

        for(ConfigCommand<T> info : commands){
            this.registerCommand(info);
        }
    }
}
