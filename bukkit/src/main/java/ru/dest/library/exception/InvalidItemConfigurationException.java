package ru.dest.library.exception;

public class InvalidItemConfigurationException extends RuntimeException{

    public InvalidItemConfigurationException(String message) {
        super("Invalid item config. Missing required key " + message);
    }
}
