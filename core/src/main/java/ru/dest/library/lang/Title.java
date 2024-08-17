package ru.dest.library.lang;

import ru.dest.library.object.IFormatAble;
import ru.dest.library.object.ISendAble;

public interface Title<T> extends ISendAble, IFormatAble<Title<T>> {

    T getTitle();
    T getSubtitle();
}
