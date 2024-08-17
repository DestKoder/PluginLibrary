package ru.dest.library.bukkit.listener;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.bukkit.LibraryMain;
import ru.dest.library.event.ItemInteractEvent;
import ru.dest.library.event.WrappedInteractEvent;
import ru.dest.library.item.IArmor;
import ru.dest.library.item.IWeapon;
import ru.dest.library.item.Item;
import ru.dest.library.item.behavior.ConsumeOnUse;
import ru.dest.library.listener.BukkitListener;
import ru.dest.library.registry.ItemsRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemsListener extends BukkitListener<LibraryMain> {

    private final Map<Arrow, IWeapon> launchedArrows = new HashMap<>();

    public ItemsListener(LibraryMain plugin) {
        super(plugin);
    }


    @EventHandler
    public void onInteract(@NotNull ItemInteractEvent event){
        if(!event.hasItem()) return;
        Item item = ItemsRegistry.current().byItemStack(Objects.requireNonNull(event.getItem()));
        if(item == null) return;

        item.onInteract(event);
        if(item instanceof ConsumeOnUse){
            ((ConsumeOnUse)item).consume(event.getPlayer(), event.getItem());
        }
    }

    @EventHandler
    public void onShoot(@NotNull EntityShootBowEvent event){
        ItemStack bow = event.getBow();
        Arrow arrow = (Arrow)event.getProjectile();
        Item bowItem = ItemsRegistry.current().byItemStack(bow);
        if(bowItem instanceof IWeapon){
            launchedArrows.put(arrow, (IWeapon) bowItem);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(@NotNull EntityDamageByEntityEvent event){
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if(damager instanceof LivingEntity){
            LivingEntity de = (LivingEntity) damager;
            if(de.getEquipment() != null){
                ItemStack hand = de.getEquipment().getItemInMainHand();
                if(hand.getType() != Material.AIR){
                    Item handItem = ItemsRegistry.current().byItemStack(hand);
                    if(handItem instanceof IWeapon){
                        ((IWeapon)handItem).handle(event);
                    }
                }
            }
        }

        if(damager instanceof Arrow){
            IWeapon weapon = launchedArrows.get((Arrow) damager);
            if(weapon != null) weapon.handle(event);
        }


        if(entity instanceof LivingEntity){
            LivingEntity le = (LivingEntity)entity;
            EntityEquipment equipment = le.getEquipment();
            if(equipment != null){
                ItemStack[] armor = le.getEquipment().getArmorContents();
                for(ItemStack item : armor){
                    Item it = ItemsRegistry.current().byItemStack(item);
                    if(it == null) continue;
                    if(it instanceof IArmor){
                        ((IArmor)it).handle(event);
                    }
                }
            }
        }

    }

}
