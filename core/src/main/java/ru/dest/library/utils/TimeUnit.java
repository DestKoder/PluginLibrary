package ru.dest.library.utils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Class represents a TimeUnit such as 1s 12d 49m 100y
 *
 * @author DestKoder
 * @since 1.0
 */
public class TimeUnit {

    private static final Map<Character, Integer> multipliers = new HashMap<>();
    static {
        multipliers.put('s', 1);
        multipliers.put('m', 60);
        multipliers.put('h', 3600);
        multipliers.put('d', 86400);
        multipliers.put('w', 604800);
        multipliers.put('M', 2592000);
        multipliers.put('y', 31536000);
    }

    private final int amount;
    private final char symbol;

    public TimeUnit(int amount, char symbol) {
        this.amount = amount;
        this.symbol = symbol;
    }

    public TimeUnit(@NotNull String s){
        if(!s.matches(Patterns.TIME_UNIT)) throw new IllegalArgumentException("Invalid string format");
        this.symbol = s.charAt(s.length()-1);
        this.amount = Integer.parseInt(s.substring(0, s.length()-1));
    }

    /**
     * Convert to seconds
     * @return seconds equivalent of TimeUnit
     */
    public long toSeconds(){
        return (long)multipliers.get(symbol)*amount;
    }

    /**
     * Convert to milliseconds
     * @return milliseconds equivalent of TimeUnit
     */
    public long toMillis(){
        return toSeconds()*1000;
    }

}
