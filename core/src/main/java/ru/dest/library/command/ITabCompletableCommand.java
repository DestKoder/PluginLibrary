package ru.dest.library.command;

import java.util.List;

/**
 * Describes a command which support tab compl
 */
public interface ITabCompletableCommand {

    List<String> getTabCompletions(Execution execution);
}
