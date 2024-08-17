package ru.dest.library.exception;

public class InvalidConnectionPropertiesException extends Exception{

    public InvalidConnectionPropertiesException() {
        super("Provided connection properties doesn't have necessary keys");
    }
}
