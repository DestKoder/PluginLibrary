package ru.dest.library.command;

import java.util.List;

public interface ICommand {

    String getName();
    List<String> getAliases();

    void execute(IExecution<?> execution);

    List<String> tabComplete(int arg, String current);
}
