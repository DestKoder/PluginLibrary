package ru.dest.library.command;

import ru.dest.library.object.ISendAble;
import ru.dest.library.plugin.IPlugin;

import java.util.List;

/**
 * Describes a command
 * @param <T>
 */
public interface ICommand<T extends IPlugin> {

    /**
     * Get name of command
     */
    String getName();

    /**
     * Get usage message of command
     */
    ISendAble getUsage();

    /**
     * Get Command aliases
     */
    List<String> getAliases();

    /**
     * Execute a command
     * @param execution {@link Execution} of command
     * @throws Exception if some occupied
     */
    void execute(Execution execution) throws Exception;

}
