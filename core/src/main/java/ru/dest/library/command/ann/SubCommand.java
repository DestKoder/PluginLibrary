package ru.dest.library.command.ann;

import ru.dest.library.command.CommandManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {

    Class<? extends CommandManager<?>> manager();
    String name();

    Class<?>[] args() default {};
    String[] permissions() default {};
    String[] aliases() default {};

    boolean playerOnly() default false;
    boolean consoleOnly() default false;
}
