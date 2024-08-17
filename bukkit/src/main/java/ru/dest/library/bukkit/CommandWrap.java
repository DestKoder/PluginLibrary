package ru.dest.library.bukkit;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;
import ru.dest.library.command.BaseCommand;
import ru.dest.library.command.Execution;
import ru.dest.library.plugin.MinecraftPlugin;

import java.util.List;

public class CommandWrap<T extends MinecraftPlugin<T, ?>> extends Command implements PluginIdentifiableCommand {

    private final BaseCommand<T> cmd;

    public CommandWrap(@NotNull BaseCommand<T> cmd) {
        super(cmd.getName(), "", "", cmd.getAliases());
        this.cmd = cmd;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Execution execution = new Execution(sender, cmd, commandLabel, args);
        //player only check
        if(cmd.isPlayerOnly() && !(sender instanceof Player)){
            Library.get().getPlayerOnlyMessage().send(sender);
            return true;
        }
        //ConsoleOnly check
        if(cmd.isConsoleOnly() && sender instanceof Player){
            Library.get().getConsoleOnlyMessage().send(sender);
            return true;
        }

        try {
            cmd.onExecute(execution);
        } catch (Exception e) {
            cmd.getPlugin().logger().warning("Error executing command " + cmd.getName() + " to " + sender.getName());
            cmd.getPlugin().logger().error(e);
            return true;
        }
        return true;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        Execution execution = new Execution(sender, cmd, alias, args);
        return cmd.getTabCompletions(execution);
    }

    @NotNull
    @Override
    public Plugin getPlugin() {
        return cmd.getPlugin();
    }
}
