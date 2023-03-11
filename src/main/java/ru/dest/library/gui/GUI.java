package ru.dest.library.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.exception.InvalidItemDataException;
import ru.dest.library.utils.ChatUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static ru.dest.library.utils.ChatUtils.applyPlaceholders;
import static ru.dest.library.utils.ChatUtils.parse;

public abstract class GUI implements InventoryHolder {

    @Nullable
    protected GUITemplate template;
    protected final Inventory inventory;
    protected final Player openFor;

    private Consumer<InventoryCloseEvent> onCloseHandler = null;
    private final Map<Integer, Consumer<InventoryClickEvent>> clickHandlers = new HashMap<>();


    public GUI(int rows, String title, Player openFor) {
        this.inventory = Bukkit.createInventory(this, rows*9, parse(title));
        this.openFor = openFor;
    }

    public GUI(int rows, String title, Player openFor, ItemStack [] items) {
        this.inventory = Bukkit.createInventory(this, rows*9, parse(title));
        this.openFor = openFor;

        if(items.length >= rows*9) {
            throw new InvalidItemDataException(items.length, rows*9);
        }

        inventory.setContents(items);
    }

    public GUI(@NotNull GUITemplate template, @NotNull Player openFor) {
        this.template = template;
        this.openFor = openFor;
        this.inventory = Bukkit.createInventory(this, template.getSlots(), applyPlaceholders(parse(template.getTitle()), openFor));

        template.getItems().forEach((slots, item) -> {
            slots.forEach(slot -> {
                ItemStack i = item.clone();
                ItemMeta meta = i.getItemMeta();

                meta.setDisplayName(ChatUtils.applyPlaceholders(meta.getDisplayName(), openFor));
                List<String> nl = new ArrayList<>();

                for(String s : meta.getLore()){
                    nl.add(ChatUtils.applyPlaceholders(s, openFor));
                }

                meta.setLore(nl);
                i.setItemMeta(meta);
                inventory.setItem(slot, i);
            });
        });
    }

    public void addClickHandler(int slot, Consumer<InventoryClickEvent> handler) {
        clickHandlers.put(slot, handler);
    }
    public void setOnClose(Consumer<InventoryCloseEvent> onClose){
        this.onCloseHandler = onClose;
    }
    public void removeOnClose(){
        this.onCloseHandler = null;
    }

    public abstract void handleClick(InventoryClickEvent event);
    public abstract void handleDrag(InventoryDragEvent event);

    public final void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        int slot = event.getSlot();

        clickHandlers.keySet().forEach(slots -> {
            if(slots == slot){
                clickHandlers.get(slots).accept(event);
            }
        });

        if(template != null) {
            template.getHandlers().keySet().forEach(slots -> {
                if(slots.contains(slot)){
                    template.getHandlers().get(slots).accept(event);
                }
            });
        }

        this.handleClick(event);
    }

    public final void onDrag(InventoryDragEvent event){
        event.setCancelled(true);
        this.handleDrag(event);
    }

    protected List<Integer> getEmptySlots(){
        List<Integer> toReturn = new ArrayList<>();

        for(int i =0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) toReturn.add(i);
        }
        return toReturn;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public final void onClose(InventoryCloseEvent event) {
        if(onCloseHandler != null) onCloseHandler.accept(event);
    }

    public void show(){
        this.openFor.openInventory(this.inventory);
    }
}
