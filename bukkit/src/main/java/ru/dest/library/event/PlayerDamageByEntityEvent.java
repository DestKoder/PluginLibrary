package ru.dest.library.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


@Getter
@Setter
public class PlayerDamageByEntityEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Entity damager;
    private boolean cancelled;
    private double damage;
    private final Player player;

    public PlayerDamageByEntityEvent(@NotNull EntityDamageByEntityEvent event) {
        setCancelled(event.isCancelled());
        this.damager = event.getDamager();
        this.damage = event.getDamage();
        this.player = (Player)event.getEntity();
        this.cancelled = false;
    }

    @Override
    public @NotNull HandlerList getHandlers(){
        return handlers;
    }


    @Contract(pure = true)
    public static @NotNull HandlerList getHandlerList(){
        return handlers;
    }

}
