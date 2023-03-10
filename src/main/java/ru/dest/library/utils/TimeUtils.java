package ru.dest.library.utils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    @NotNull
    public static String formatUnixTime(long unixTime, String format) {
        return new SimpleDateFormat(format).format(new Date(unixTime*1000));
    }

    @NotNull
    public static String formatTimeInMillis(long time, String format){
        return new SimpleDateFormat(format).format(new Date(time));
    }

    public static long getUnixTime() {
        return System.currentTimeMillis()/1000;
    }
}
