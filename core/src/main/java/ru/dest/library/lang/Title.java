package ru.dest.library.lang;

import ru.dest.library.object.IFormatAble;
import ru.dest.library.object.ISendAble;

/**
 * Describe a Title which can be shown to a player
 * @param <T>
 */
public interface Title<T> extends ISendAble, IFormatAble<Title<T>> {

    T getTitle();
    T getSubtitle();
}
