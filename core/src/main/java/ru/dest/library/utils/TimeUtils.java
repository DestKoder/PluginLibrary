package ru.dest.library.utils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilities for working with time
 *
 * @since 1.0
 * @author DestKoder
 */
public final class TimeUtils {

    /**
     * Some default time format values
     *
     * @since 2.0
     * @author DestKoder
     */
    public static class DEFAULT_TIME_FORMAT {
        public static final String DD_MM_YYYY_POINTS = "dd.MM.yyyy";
        public static final String DD_MM_YYYY_SLASH = "dd/MM/yyyy";
        public static final String HH_MM = "HH:mm";
        public static final String HH_MM_SS = "HH:mm:ss";
        public static final String FULL_POINTS = HH_MM_SS + " " + DD_MM_YYYY_POINTS;
        public static final String FULL_SLASH = HH_MM_SS + " " + DD_MM_YYYY_SLASH;
    }

    /**
     * Parse date string to time in milliseconds
     * @param date {@link String} in DD_MM_YYYY_POINTS default time format;
     * @return time in milliseconds from unix epoch start
     * @throws ParseException if format is invalid
     */
    public long getTimeForDateString(String date) throws ParseException {
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT.DD_MM_YYYY_POINTS).parse(date).getTime();
    }

    /**
     * Parse date string to time in seconds (unix time)
     * @param date {@link String} in DD_MM_YYYY_POINTS default time format;
     * @return time in seconds since 1 Jan 1970
     * @throws ParseException Invalid Input Format
     */
    public long getUnixTimeForDateString(String date) throws ParseException {
        return getTimeForDateString(date)/1000;
    }

    /**
     * Format time in seconds to pointed format
     * @param unixTime - time in seconds since 1 Jan 1970
     * @param format - date format
     * @return unix time in pointed date format
     */
    @NotNull
    public static String formatUnixTime(long unixTime, String format) {
        return formatTimeInMillis(unixTime*1000, format);
    }

    /**
     * Format time in milliseconds to pointed format
     * @param time - time in milliseconds since 1 Jan 1970
     * @param format - date format
     * @return time in pointed date format
     */
    @NotNull
    public static String formatTimeInMillis(long time, String format){
        return new SimpleDateFormat(format).format(new Date(time));
    }

    /**
     * Get current time since 1 jan 1970 in seconds
     * @return time since 1 jav 1970 in seconds
     */
    public static long getCurrentUnixTime() {
        return getCurrentTime()/1000;
    }

    /**
     * Get current time since 1 jan 1970 in milliseconds
     * @return time since 1 jan 1970 in milliseconds
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
