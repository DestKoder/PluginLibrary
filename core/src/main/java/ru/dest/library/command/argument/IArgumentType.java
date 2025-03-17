package ru.dest.library.command.argument;

import ru.dest.library.lang.Message;
import ru.dest.library.object.ISendAble;

import java.util.List;

public interface IArgumentType {
    boolean isValid(String arg);
    List<String> getCompletions();

    default Message invalidMessage() {
        return null;
    }

}
