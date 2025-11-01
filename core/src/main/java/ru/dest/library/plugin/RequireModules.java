package ru.dest.library.plugin;

import ru.dest.library.module.Module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describe a modules without which plugin will not run
 * only work on general plugin class
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireModules {

    Class<? extends Module>[] modules();
}
