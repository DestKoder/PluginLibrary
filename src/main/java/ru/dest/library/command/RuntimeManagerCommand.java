package ru.dest.library.command;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.exception.SubCommandExistsException;

import java.util.ArrayList;
import java.util.List;

public class RuntimeManagerCommand<T extends JavaPlugin> extends RuntimeCommand<T> implements ManagerCommand<T>{

    private final List<ICommand<T>> subCommands = new ArrayList<>();

    public RuntimeManagerCommand(T plugin, @NotNull String name) {
        super(plugin, name);
    }

    public RuntimeManagerCommand(T plugin, @NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(plugin, name, description, usageMessage, aliases);
    }

    @Override
    public void addSubCommand(ICommand<T> cmd) {
        for(String s : cmd.getAliases()){
            if(getCommandByName(s) != null) {
                throw new SubCommandExistsException(s);
            }
        }

        this.subCommands.add(cmd);
    }

    @Override
    public final void execute(CommandData data, String[] args) {
        if(args.length < 1){
            data.getSender().sendMessage(this.usageMessage);
            return;
        }

        ICommand<T> sub = getCommandByName(args[0]);

        if(sub == null) {
            data.getSender().sendMessage(this.usageMessage);
            return;
        }

        CommandData cd = new CommandData(args, data.getSender(), this, args[0]);

        sub.execute(cd, cd.getArgs());
    }

    @Nullable
    protected ICommand<T> getCommandByName(@NotNull String name){
        for(ICommand<T> cmd : subCommands){
            if(cmd.getAliases().contains(name) ||cmd.getName().equalsIgnoreCase(name)) return cmd;
        }
        return null;
    }
}
