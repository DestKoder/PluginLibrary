package ru.dest.library.command.argument;


import ru.dest.library.command.ann.Command;
import ru.dest.library.lang.Message;
import ru.dest.library.object.ISendAble;

import java.util.List;

/**
 * Describe an argument type which can be passed
 * into {@link Command} args
 */
public interface IArgumentType<T> {

    boolean isValid(String arg);

    default Message invalidMessage() {
        return null;
    }

    List<String> getCompletions(String arg);

    T get(String val);
}
