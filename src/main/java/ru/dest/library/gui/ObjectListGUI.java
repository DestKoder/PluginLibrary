package ru.dest.library.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import ru.dest.library.object.IItem;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * This class represents a basic object-list gui
 * @param <ItemObject>> Object Class which realizing interface IHoldItemStack
 */
public class ObjectListGUI<ItemObject extends IItem> extends GUI{

    protected List<ItemObject> items;
    protected List<Integer> emptySlots;

    protected final BiConsumer<InventoryClickEvent, ItemObject> onItemClick;

    protected int page, pages;

    public ObjectListGUI(GUITemplate template, Player opener, List<ItemObject> items, BiConsumer<InventoryClickEvent, ItemObject> onItemClick) {
        super(template, opener);
        this.items = items;
        this.emptySlots = getEmptySlots();
        this.page = 0;
        this.pages = items.size() % emptySlots.size() == 0 ? items.size() / emptySlots.size() : items.size() / emptySlots.size()+1;
        this.onItemClick = onItemClick;

        this.refillContent();

        this.addClickHandler(template.getSystemHandlers().get("next"), event -> {
            if(page < pages) {
                page++;
                refillContent();
            }
        });

        this.addClickHandler(template.getSystemHandlers().get("prev"), event -> {
            if(page > 0) {
                page--;
                refillContent();
            }
        });
    }

    private void refillContent(){
        this.clearContent();
        for(int i = 0; i < emptySlots.size(); i++){
            int item = i + (page == 0 ? 0 : page*emptySlots.size());

            if(item < items.size()){
                inventory.setItem(emptySlots.get(i), items.get(item).getItem());
            }
        }
    }
    private void clearContent(){
        for(int i : emptySlots){
            inventory.setItem(i, new ItemStack(Material.AIR));
        }
    }


    @Override
    public void handleClick(InventoryClickEvent event) {
        int slot = event.getSlot();

        if(inventory.getItem(slot) == null || inventory.getItem(slot).getType() == Material.AIR) return;
        if(this.onItemClick == null) return;
        if(!emptySlots.contains(slot)) return;

        int itemPos = page == 0 ? slot-emptySlots.get(0) :  this.page * this.emptySlots.size() + slot;

        if(items.size() < itemPos) {
            System.out.println("Internal error item size " + items.size() +" < itemPos " + itemPos +". Page: " + page + "; Slot: " + (slot-emptySlots.get(0)));
            return;
        }

        ItemObject item = items.get(itemPos);

        this.onItemClick.accept(event, item);
    }

    @Override
    public void handleDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

}
