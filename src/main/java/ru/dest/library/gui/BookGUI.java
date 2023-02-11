package ru.dest.library.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookGUI {

    private static final ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
    private final Player openFor;
    private final BookMeta meta;

    public BookGUI(Player openFor, String[]... pages) {
        this.openFor = openFor;
        meta = (BookMeta) book.getItemMeta();
        assert meta != null;

        for(String[] str : pages){
            meta.addPage(str);
        }
    }

    public void show() {
        openFor.closeInventory();
        ItemStack gui = book.clone();
        gui.setItemMeta(meta);

        openFor.openBook(book);
    }

}
