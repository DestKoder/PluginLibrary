package ru.dest.library.logging;

/**
 * Interface which describes any logger
 *
 * @since 1.0
 * @author DestKoder
 */
public interface ILogger {

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
