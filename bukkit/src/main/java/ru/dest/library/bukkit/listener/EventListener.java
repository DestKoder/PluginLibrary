package ru.dest.library.bukkit.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.bukkit.LibraryMain;
import ru.dest.library.event.*;
import ru.dest.library.listener.BukkitListener;
import ru.dest.library.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class EventListener extends BukkitListener<LibraryMain> {

    public EventListener(LibraryMain plugin) {
        super(plugin);
    }

    private final Map<Player, Long> lastInteract = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void interact(@NotNull WrappedInteractEvent event){
        if(event.hasBlock()){
            assert event.getClickedBlock() != null;
            BlockInteractEvent blockEvent = new BlockInteractEvent(event.getPlayer(), event.getAction(), event.getItem(), event.getClickedBlock(), event.getBlockFace(), event.getHand());
            LibraryMain.getInstance().getServer().getPluginManager().callEvent(blockEvent);
            event.setCancelled(blockEvent.isCancelled());
        }

        if(event.hasItem()){
            assert event.getItem()!= null;
            ItemInteractEvent itemEvent = new ItemInteractEvent(event.getPlayer(), event.getAction(), event.getItem(), event.getClickedBlock(), event.getBlockFace(), event.getHand());
            LibraryMain.getInstance().getServer().getPluginManager().callEvent(itemEvent);
            event.setCancelled(itemEvent.isCancelled());
        }
    }

    @EventHandler(ignoreCancelled = true)
    @SuppressWarnings("deprecation")
    public void onInteract(@NotNull PlayerInteractEvent event){
        if(lastInteract.containsKey(event.getPlayer())) if(TimeUtils.getCurrentTime()-lastInteract.get(event.getPlayer()) < 50) return;
        lastInteract.put(event.getPlayer(), TimeUtils.getCurrentTime());

        WrappedInteractEvent ev = new WrappedInteractEvent(event);
        plugin.getServer().getPluginManager().callEvent(ev);
        event.setCancelled(ev.isCancelled());
    }

    @EventHandler()
    public void onDamage(@NotNull EntityDamageByEntityEvent event){
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if(entity instanceof Player){
            PlayerDamageByEntityEvent ev = new PlayerDamageByEntityEvent(event);
            event.setCancelled(ev.isCancelled());
            event.setDamage(ev.getDamage());
            plugin.getServer().getPluginManager().callEvent(ev);

            if(ev.isCancelled())return;

            if(damager instanceof Player){
                PlayerDamageByPlayerEvent ev1 = new PlayerDamageByPlayerEvent(event);
                plugin.getServer().getPluginManager().callEvent(ev1);
                event.setCancelled(ev1.isCancelled());
                event.setDamage(ev1.getDamage());
            }
        }

    }

}
