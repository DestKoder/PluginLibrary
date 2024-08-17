package ru.dest.library.exception;

public class InvalidMaterialException extends RuntimeException{

    public InvalidMaterialException(String message) {
        super("Unknown material " + message);
    }
}
