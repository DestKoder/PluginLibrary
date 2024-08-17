package ru.dest.library.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.FormatPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class ListingGUI<T> extends GUI{

    private final List<T> items;
    private final List<Integer> emptySlots;

    protected BiConsumer<InventoryClickEvent, T> onItemClickHandler;

    protected int page, pages;

    private final Map<Integer, T> pageItems = new HashMap<>();

    public ListingGUI(@NotNull GUITemplate template, Player player, @NotNull List<T> items, FormatPair... format) {
        super(template, player, format);
        this.items = items;
        this.emptySlots = getEmptySlots();
        this.page = 0;
        this.pages = items.size() % emptySlots.size() == 0 ? items.size() / emptySlots.size() : items.size() / emptySlots.size() + 1;

        refillContent();

        this.setHandler("next", (event -> {
            if(page < pages){
                page ++;
                refillContent();
            }
        }));

        this.setHandler("prev", (event -> {
            if(page > 0) {
                page--;
                refillContent();
            }
        }));
    }

    protected final @NotNull List<Integer> getEmptySlots(){
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < inventory.getSize(); i ++ ){
            if(inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) result.add(i);
        }
        return result;
    }

    private void clearContent(){
        for(int i : emptySlots){
            inventory.setItem(i, new ItemStack(Material.AIR));
        }
        pageItems.clear();
    }

    private void refillContent(){
        this.clearContent();
        for(int i = 0; i < emptySlots.size(); i ++){
            int item = i + (page == 0 ? 0 : page*emptySlots.size());
            if(item < items.size()){
                inventory.setItem(emptySlots.get(i), makeItem(items.get(item)));
                pageItems.put(emptySlots.get(i), items.get(item));
            }
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if(onItemClickHandler == null) return;
        int slot = event.getSlot();
        if(!emptySlots.contains(slot)) return;
        if(inventory.getItem(slot) == null || inventory.getItem(slot).getType() == Material.AIR) return;

        if(!pageItems.containsKey(slot)) return;

        this.onItemClickHandler.accept(event, pageItems.get(slot));
    }

    protected abstract ItemStack makeItem(T item);

}
