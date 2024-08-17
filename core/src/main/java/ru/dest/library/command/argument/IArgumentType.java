package ru.dest.library.command.argument;

import java.util.List;

public interface IArgumentType {
    boolean isValid(String arg);
    List<String> getCompletions();
}
