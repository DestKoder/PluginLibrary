package ru.dest.library.command;

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigCommand<T extends JavaPlugin> implements ICommand<T>, CommandExecutor {

    protected final T plugin;
    protected final List<String> aliases;
    protected final String name;

    public ConfigCommand(T plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.aliases = new ArrayList<>();
        aliases.add(name);
    }

    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandData data = new CommandData(args, sender, command, label);

        this.execute(data, data.getArgs());
        return true;
    }

    public abstract void execute(CommandData data, String[] args);

    @Override
    public final @NotNull T getPlugin() {
        return plugin;
    }

    @Override
    public final @NotNull List<String> getAliases() {
        return null;
    }
}
