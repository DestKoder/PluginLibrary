package ru.dest.library.exception;

public class InvalidGUIConfigurationException extends RuntimeException{

    public InvalidGUIConfigurationException(String message) {
        super("Invalid gui configuration. " + message);

    }
}
