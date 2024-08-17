package ru.dest.library.event;

import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class WrappedInteractEvent extends PlayerInteractEvent {

    private static final HandlerList handlers = new HandlerList();

    public WrappedInteractEvent(@NotNull PlayerInteractEvent event){
        super(event.getPlayer(), event.getAction(),event.getItem(),event.getClickedBlock(),event.getBlockFace());
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static @NotNull HandlerList getHandlerList(){
        return handlers;
    }
}
