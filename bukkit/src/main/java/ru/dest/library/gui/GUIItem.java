package ru.dest.library.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.dest.library.config.ItemConfig;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public class GUIItem {

    private final ItemConfig config;
    private final int[] slots;
    private final List<String> rightClickActions, leftClickActions, clickActions, middleClickActions;

    @Override
    public String toString() {
        return "GUIItem{" +
                "config=" + config +
                ", slots=" + Arrays.toString(slots) +
                ", rightClickActions=" + rightClickActions +
                ", leftClickActions=" + leftClickActions +
                ", clickActions=" + clickActions +
                ", middleClickActions=" + middleClickActions +
                '}';
    }
}
