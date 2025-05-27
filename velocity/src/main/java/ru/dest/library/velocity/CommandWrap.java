package ru.dest.library.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;
import ru.dest.library.command.BaseCommand;
import ru.dest.library.command.Execution;

public class CommandWrap implements SimpleCommand {

    private final BaseCommand<?> command;

    public CommandWrap(BaseCommand<?> command) {
        this.command = command;
    }

    @Override
    public void execute(@NotNull Invocation invocation) {
        CommandSource sender = invocation.source();
        Execution execution = new Execution(sender, command, invocation.alias(), invocation.arguments());

        if(command.isPlayerOnly() && !(sender instanceof Player)){
            Library.get().getPlayerOnlyMessage().send(sender);
            return;
        }
        //ConsoleOnly check
        if(command.isConsoleOnly() && sender instanceof Player){
            Library.get().getConsoleOnlyMessage().send(sender);
            return;
        }

        try {
            command.onExecute(execution);
        } catch (Exception e) {
            command.getPlugin().logger().warning("Error executing command " + command.getName() + " to " + (sender instanceof Player ? ((Player)sender).getUsername() : "CONSOLE"));
            command.getPlugin().logger().error(e);
            return;
        }
    }


}
