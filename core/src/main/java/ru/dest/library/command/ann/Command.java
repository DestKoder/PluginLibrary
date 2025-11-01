package ru.dest.library.command.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CommandInfo for plugin-only args constructors
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
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
