package ru.dest.library.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import ru.dest.library.bukkit.LibraryMain;
import ru.dest.library.gui.GUI;
import ru.dest.library.listener.BukkitListener;

public class GUIListener extends BukkitListener<LibraryMain> {

    public GUIListener(LibraryMain plugin) {
        super(plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getClickedInventory() == null || event.getClickedInventory().getHolder() == null) return;
        if(!(event.getClickedInventory().getHolder() instanceof GUI)) return;

        ((GUI)event.getClickedInventory().getHolder()).h(event);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        if(event.getInventory().getHolder() == null || !(event.getInventory().getHolder() instanceof GUI)) return;

        ((GUI)event.getInventory().getHolder()).h(event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(event.getInventory().getHolder() == null || !(event.getInventory().getHolder() instanceof GUI)) return;

        ((GUI)event.getInventory().getHolder()).h(event);
    }
}
