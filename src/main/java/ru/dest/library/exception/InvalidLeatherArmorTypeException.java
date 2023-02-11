package ru.dest.library.exception;

public class InvalidLeatherArmorTypeException extends RuntimeException{

    public InvalidLeatherArmorTypeException(String type) {
        super("Material type " + type +" isn't leather armor!");
    }
}
