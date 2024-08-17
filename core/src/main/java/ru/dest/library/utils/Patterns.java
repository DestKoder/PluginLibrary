package ru.dest.library.utils;

/**
 * Some common regex patterns used in coding
 * @since 1.0
 * @author DestKoder
 */
public final class Patterns {
    public static final String ANY = "^.*$";
    public static final String INTEGER = "^[0-9]+$";
    public static final String DOUBLE = "^[0-9]+\\.[0-9]+$";
    public static final String BOOLEAN = "^(true|false)$";
    public static final String TIME_UNIT = "^[0-9]+[smhdwMy]$";
    public static final String IP_V4 = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$";
    public static final String EMAIL = "^[0-9a-zA-Z_\\-\\.]+@[A-Za-z]+\\.+[a-zA-Z]+$";
}
