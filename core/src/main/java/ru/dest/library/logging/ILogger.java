package ru.dest.library.logging;

/**
 * Interface which describes any logger
 *
 * @since 1.0
 * @author DestKoder
 */
public interface ILogger {

    String RESET = "\u001B[0m";
    String BLACK = "\u001B[30m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String BLUE = "\u001B[34m";
    String PURPLE = "\u001B[35m";
    String CYAN = "\u001B[36m";
    String WHITE = "\u001B[37m";

    /**
     * Print info color messages in console
     * @param msg messages to print
     */
    void info(String... msg);

    /**
     * Print warning color messages in console
     * @param warnings messages to print
     */
    void warning(String... warnings);

    /**
     * Print errors to console
     * @param errors {@link Exception}s to print
     */
    void error(Exception... errors);

}
