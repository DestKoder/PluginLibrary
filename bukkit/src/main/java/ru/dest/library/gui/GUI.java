package ru.dest.library.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.helper.ActionExecutor;
import ru.dest.library.object.FormatPair;
import ru.dest.library.utils.ColorUtils;
import ru.dest.library.utils.FormatUtils;

import java.util.*;
import java.util.function.Consumer;

public abstract class GUI implements InventoryHolder {

    //System
    private final List<Player> viewers = new ArrayList<>();
    private final Map<Integer, Consumer<InventoryClickEvent>> handlers = new HashMap<>();
    protected final Inventory inventory;
    private GUITemplate template;

    public GUI(@NotNull GUITemplate template, Player player, FormatPair... format){
        this.inventory = Bukkit.createInventory(this, template.getRows()*9, ColorUtils.parse(FormatUtils.format(template.getTitle(), format)));
        this.template = template;

        fill(template.getItems(), player, format);
    }

    public GUI(int rows){
        this.inventory = Bukkit.createInventory(this, rows*9);
    }

    public GUI(int rows, String title, FormatPair... format){
        this.inventory = Bukkit.createInventory(this, rows*9, ColorUtils.parse(FormatUtils.format(title, format)));
    }


    protected final void setHandler(String systemId, Consumer<InventoryClickEvent> handler){
        Arrays.stream(template.getHandler(systemId)).forEach( i -> handlers.put(i, handler));
    }

    private void fill(@NotNull List<GUIItem> items, Player player, FormatPair... format){
        items.forEach(item -> {
            ItemStack bItem = item.getConfig().makeItem(player, format);

            for (int slot : item.getSlots()) {

                inventory.setItem(slot, bItem);
                handlers.put(slot, (event -> {
                    item.getClickActions().forEach(action -> {
                        ActionExecutor.executeAction((Player) event.getWhoClicked(), action);
                    });
                    if(event.getClick() == ClickType.LEFT){
                        item.getLeftClickActions().forEach(action -> {
                            ActionExecutor.executeAction((Player) event.getWhoClicked(), action);
                        });
                    }
                    if(event.getClick() == ClickType.RIGHT){
                        item.getRightClickActions().forEach(action -> {
                            ActionExecutor.executeAction((Player) event.getWhoClicked(), action);
                        });
                    }
                    if(event.getClick() == ClickType.MIDDLE){
                        item.getMiddleClickActions().forEach(action -> {
                            ActionExecutor.executeAction((Player) event.getWhoClicked(), action);
                        });
                    }
                }));
            }
        });
    }


    public final void h(@NotNull InventoryClickEvent event){
        event.setCancelled(true);
        if(handlers.containsKey(event.getSlot())){
            handlers.get(event.getSlot()).accept(event);
        }
        this.onClick(event);
    }

    public final void h(@NotNull InventoryCloseEvent event){
        this.viewers.remove((Player) event.getPlayer());
        this.onClose(event);
    }

    public final void h(@NotNull InventoryDragEvent event){
        event.setCancelled(true);
        this.onDrag(event);
    }

    public void onDrag(InventoryDragEvent event){}
    public void onClick(InventoryClickEvent event){};
    public void onClose(InventoryCloseEvent event){};

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void show(@NotNull Player player){
        player.openInventory(getInventory());
        viewers.add(player);
    }

}
