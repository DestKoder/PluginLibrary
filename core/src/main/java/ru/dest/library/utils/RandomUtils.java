package ru.dest.library.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Utilities for working with random numbers
 *
 * @since 1.0
 * @author DestKoder
 */
public final class RandomUtils {

    /**
     * Get random integer in min-max range. maximum value included
     * @param min - minimal value
     * @param max - maximal value
     * @return random integer within the specified range
     */
    public static int randomIntIncludeMax(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

    /**
     * Get random integer in min-max range. maximum value not included
     * @param min - minimal value
     * @param max - maximal value
     * @return random integer within the specified range
     */
    public static int randomInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * Get random integer in 0-max range. maximum value included
     * @param max - maximal value
     * @return random integer within the specified range
     */
    public static int randomIntIncludeMax(int max){
        return ThreadLocalRandom.current().nextInt(max+1);
    }

    /**
     * Get random integer in 0-max range. maximum value not included
     * @param max - maximal value
     * @return random integer within the specified range
     */
    public static int randomInt(int max){
        return ThreadLocalRandom.current().nextInt(max);
    }


    /**
     * Get random double in min-max range. maximum value included
     * @param min - minimal value
     * @param max - maximal value
     * @return random double within the specified range
     */
    public static double randomDoubleIncludeMax(double min, double max){
        return ThreadLocalRandom.current().nextDouble(min, max+1);
    }
    /**
     * Get random double in min-max range. maximum value not included
     * @param min - minimal value
     * @param max - maximal value
     * @return random double within the specified range
     */
    public static double randomDouble(double min, double max){
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
    /**
     * Get random double in 0-max range. maximum value included
     * @param max - maximal value
     * @return random double within the specified range
     */
    public static double randomDoubleIncludeMax(double max){
        return ThreadLocalRandom.current().nextDouble(max+0.1);
    }
    /**
     * Get random double in 0-max range. maximum value not included
     * @param max - maximal value
     * @return random double within the specified range
     */
    public static double randomDouble(double max){
        return ThreadLocalRandom.current().nextDouble(max);
    }

}
