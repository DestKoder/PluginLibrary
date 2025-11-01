package ru.dest.library.utils;

import lombok.experimental.UtilityClass;


/**
 * Some utilities for work with chance
 */
@UtilityClass
public class ChanceUtils {

    /**
     * Make some action if chance
     * @param r {@link Runnable} to ran
     * @param chance what is chance
     */
    public void doWithChance(Runnable r, double chance) {
        if(chance <= Math.random()) r.run();
    }

    /**
     * Convert a percent chance to double chance
     * @param percents chance of action in percents
     * @return a double equivalent of passed chance
     */
    public double chanceFromPercents(int percents){
        return ((double)percents / 100 );
    }
}
