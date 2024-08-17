package ru.dest.library.object;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class describes a pair of two value;
 *
 * @since 1.0
 *
 * @param <F> Type of first value
 * @param <V> Typy of sceond value
 */
@Data
@AllArgsConstructor
public class Pair<F,V> {

    private F firstValue;
    private V secondValue;

}
