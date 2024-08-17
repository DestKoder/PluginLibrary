package ru.dest.library.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.FormatPair;

import java.util.List;

public final class FormatUtils {

    @Contract(pure = true)
    public static @NotNull String format(@NotNull String what, String key, String value){
        return what.replaceAll("\\{"+ key+"\\}", value);
    }

    public static String format(String what, FormatPair @NotNull ... format){
        for(FormatPair p : format){
            what = format(what, p.getFirstValue(), p.getSecondValue().toString());
        }
        return what;
    }

    public static String format(String what, @NotNull List<FormatPair> format){
        return format(what, format.toArray(new FormatPair[0]));
    }

}
