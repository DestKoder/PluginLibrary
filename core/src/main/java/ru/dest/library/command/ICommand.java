package ru.dest.library.command;

import ru.dest.library.object.ISendAble;
import ru.dest.library.plugin.IPlugin;

import java.util.List;

public interface ICommand<T extends IPlugin> {

    String getName();
    ISendAble getUsage();
    List<String> getAliases();

    void execute(Execution execution) throws Exception;

}
