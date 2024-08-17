package ru.dest.library.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PlayerDamageByPlayerEvent extends EntityDamageByEntityEvent {

    private static final HandlerList handlers = new HandlerList();

    public PlayerDamageByPlayerEvent(@NotNull EntityDamageByEntityEvent event) {
        super(event.getDamager(), event.getEntity(), event.getCause(), event.getDamage());
        setCancelled(event.isCancelled());
    }

    public @NotNull Player getDamager(){
        return (Player) super.getDamager();
    }

    public HandlerList getHandler(){
        return handlers;
    }

    public Player getPlayer(){
        return (Player) getEntity();
    }

    @Contract(pure = true)
    public static @NotNull HandlerList getHandlerList(){
        return handlers;
    }
}
