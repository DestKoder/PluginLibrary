package ru.dest.library.item;


import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

public interface IWeapon {

    default void handle(@NotNull EntityDamageByEntityEvent event){
        event.setDamage(getDamage());
    }

    int getDamage();

}
