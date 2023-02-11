package ru.dest.library.exception;


/**
 * Exception thrown when attempting to load sth from {@link org.bukkit.configuration.Configuration}, but some needed keys are missing
 *
 * @since 1.0
 * @author DestKoder
 */
public class MissingConfigurationException extends RuntimeException{

    public MissingConfigurationException(){
        super("Invalid configuration! One of values is missing");
    }

    public MissingConfigurationException(String message) {
        super(message);
    }
}
