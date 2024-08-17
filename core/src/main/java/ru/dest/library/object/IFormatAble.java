package ru.dest.library.object;

import java.util.List;

public interface IFormatAble<R> {

    R format(String key, String value);
    R format(FormatPair... format);
    R format(List<FormatPair> format);

}
