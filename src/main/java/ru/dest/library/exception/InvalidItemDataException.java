package ru.dest.library.exception;

public class InvalidItemDataException extends RuntimeException{

    public InvalidItemDataException(int sizeItems, int sizeInv) {
        super("Item data array couldn't have size more than inventory size. " + sizeItems + " > " + sizeInv);
    }
}
