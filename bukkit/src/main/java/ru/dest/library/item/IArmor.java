package ru.dest.library.item;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

public interface IArmor {

    default void handle(@NotNull EntityDamageByEntityEvent event){
        event.setDamage(event.getDamage()-getProtection());
    }

    int getProtection();

}
