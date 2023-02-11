package ru.dest.library.exception;

/**
 * Thrown if the specified material does not exist.
 *
 * @since 1.0
 * @author DestKoder
 */
public class InvalidMaterialException extends RuntimeException{

    public InvalidMaterialException(String material) {
        super("Material with name " + material + " does not exists!");
    }
}
