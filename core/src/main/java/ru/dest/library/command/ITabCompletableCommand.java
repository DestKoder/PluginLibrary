package ru.dest.library.command;

import java.util.List;

public interface ITabCompletableCommand {

    List<String> getTabCompletions(Execution execution);
}
