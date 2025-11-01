package ru.dest.library.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.FormatPair;

import java.util.List;

/**
 * Class for work with Library formatting
 */
public final class FormatUtils {

    /**
     * Format string
     * @param what string to format
     * @param key key for search
     * @param value replacement
     * @return formatted string
     */
    @Contract(pure = true)
    public static @NotNull String format(@NotNull String what, String key, String value){
        return what.replaceAll("\\{"+ key+"\\}", value);
    }

    /**
     * Format string using a FormatPair
     * @param what string to format
     * @param format format pair
     * @return formatted string
     */
    public static String format(String what, FormatPair @NotNull ... format){
        for(FormatPair p : format){
            what = format(what, p.getFirstValue(), p.getSecondValue().toString());
        }
        return what;
    }

    /**
     * Format string using a List of FormatPair
     * @param what string to format
     * @param format List of {@link FormatPair}
     * @return formatted string
     */
    public static String format(String what, @NotNull List<FormatPair> format){
        return format(what, format.toArray(new FormatPair[0]));
    }

}
