package ru.dest.library.exception;

public class UnsupportedFileTypeException extends RuntimeException{

    public UnsupportedFileTypeException(String type) {
        super("File type with extension " +type+" doesn't supported yet");
    }
}
