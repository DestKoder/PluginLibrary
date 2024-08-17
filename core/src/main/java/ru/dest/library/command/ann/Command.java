package ru.dest.library.command.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String value();
    Class<?>[] args() default {};
    String[] permissions() default {};
    String[] aliases() default {};

    boolean playerOnly() default false;
    boolean consoleOnly() default false;
}
