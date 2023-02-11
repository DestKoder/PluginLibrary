package ru.dest.library.exception;

public class SubCommandExistsException extends RuntimeException{

    public SubCommandExistsException(String name) {
        super("Can't register command " + name +". This name used by another command");
    }
}
