package ru.dest.library.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlayerDamageByPlayerEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player damager;
    @Setter
    private double damage;
    private final Player player;

    @Setter
    private boolean cancelled;

    public PlayerDamageByPlayerEvent(@NotNull EntityDamageByEntityEvent event) {
        this.damager = (Player)event.getDamager();
        this.cancelled = false;
        this.damage = event.getDamage();
        this.player = (Player) event.getEntity();
        setCancelled(event.isCancelled());
    }


    public @NotNull HandlerList getHandlers(){
        return handlers;
    }


    @Contract(pure = true)
    public static @NotNull HandlerList getHandlerList(){
        return handlers;
    }
}
