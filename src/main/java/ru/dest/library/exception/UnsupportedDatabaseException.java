package ru.dest.library.exception;

public class UnsupportedDatabaseException extends RuntimeException {

    public UnsupportedDatabaseException(String type) {
        super("Database type " + type + " isn't supported");
    }
}
