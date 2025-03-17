package ru.dest.library.command.argument;

import ru.dest.library.object.ISendAble;

import java.util.List;

public interface IArgumentType {

    boolean isValid(String arg);
    List<String> getCompletions(String arg);

    default ISendAble getErrorMessage(){return null;};
}
