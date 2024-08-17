package ru.dest.library.item.behavior;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public interface ConsumeOnUse extends ItemBehavior{

    default void consume(Player player, @NotNull ItemStack item){
        item.setAmount(item.getAmount());
    }

    @Override
    default void onItemCreate(ItemMeta meta){}
}
