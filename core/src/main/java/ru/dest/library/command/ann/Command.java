package ru.dest.library.command.ann;

import java.lang.annotation.*;

/**
 * CommandInfo for plugin-only args constructors
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Command {

    /**
     * Name of command
     */
    String value();

    /**
     * Command arguments
     */
    Class<?>[] args() default {};

    /**
     * Command permissions
     */
    String[] permissions() default {};

    /**
     * Command aliases
     */
    String[] aliases() default {};

    /**
     * Is command can be executed only by players
     */
    boolean playerOnly() default false;

    /**
     * Is command can be executed only by console
     */
    boolean consoleOnly() default false;
}
