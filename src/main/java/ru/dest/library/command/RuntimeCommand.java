package ru.dest.library.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class RuntimeCommand<T extends JavaPlugin> extends Command implements ICommand<T> {

    protected final T plugin;

    public RuntimeCommand(T plugin, @NotNull String name) {
        super(name);
        this.plugin = plugin;
    }

    public RuntimeCommand(T plugin, @NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.plugin = plugin;
    }

    @Override
    public final boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        CommandData data = new CommandData(args, sender, this, label);

        this.execute(data, data.getArgs());
        return true;
    }

    @Override
    public abstract void execute(CommandData data, String[] args);

    @Override
    public final @NotNull T getPlugin() {
        return plugin;
    }
}
