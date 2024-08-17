package ru.dest.library.exception;

public class UnknownDatabaseDriver extends Exception{

    public UnknownDatabaseDriver(String message) {
        super("Unknown database driver " + message);
    }
}
