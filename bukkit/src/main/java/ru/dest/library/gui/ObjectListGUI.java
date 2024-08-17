package ru.dest.library.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.item.IItem;
import ru.dest.library.object.FormatPair;

import java.util.List;

public class ObjectListGUI<T extends IItem> extends ListingGUI<T>{

    public ObjectListGUI(@NotNull GUITemplate template, Player player, @NotNull List<T> items, FormatPair... format) {
        super(template, player, items, format);
    }

    @Override
    protected ItemStack makeItem(@NotNull T item) {
        return item.get();
    }
}
