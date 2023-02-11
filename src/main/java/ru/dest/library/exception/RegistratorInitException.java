package ru.dest.library.exception;

/**
 * @since 1.0
 * @author DestKoder
 */
public class RegistratorInitException extends Exception{

    private Exception error;

    public RegistratorInitException(String message, Exception error) {
        super(message, error);
        this.error = error;
    }

    public Exception getError() {
        return error;
    }
}
