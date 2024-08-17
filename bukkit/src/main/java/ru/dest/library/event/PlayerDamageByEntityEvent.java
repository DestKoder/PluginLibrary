package ru.dest.library.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


@Getter
public class PlayerDamageByEntityEvent extends EntityDamageByEntityEvent {

    private static final HandlerList handlers = new HandlerList();

    public PlayerDamageByEntityEvent(@NotNull EntityDamageByEntityEvent event) {
        super(event.getDamager(), event.getEntity(), event.getCause(), event.getDamage());
        setCancelled(event.isCancelled());
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
