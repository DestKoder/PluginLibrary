package ru.dest.library.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.bukkit.LibraryMain;
import ru.dest.library.gui.GUI;

import java.util.Objects;

@UtilityClass
public class PlayerUtils {

    public String getIp(@NotNull Player player){
        return Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress();
    }

    public void open(Player player, GUI gui){
        LibraryMain.getInstance().sync(()-> gui.show(player));
    }

    public void give(@NotNull Player player, ItemStack item){
        int slot = player.getInventory().first(item);

        if(slot != -1){
            ItemStack i = player.getInventory().getItem(slot);
            assert  i != null;

            if(i.getAmount() + item.getAmount() > i.getMaxStackSize()){
                item.setAmount(item.getAmount() + i.getAmount() - i.getMaxStackSize());
                i.setAmount(i.getMaxStackSize());
                slot = player.getInventory().firstEmpty();
                if(slot != -1) player.getInventory().addItem(item);
                else player.getWorld().dropItemNaturally(player.getLocation(), item);
                return;
            }
            i.setAmount(i.getAmount() + item.getAmount());
            player.getInventory().setItem(slot, i);
            return;
        }
        slot = player.getInventory().firstEmpty();
        if(slot != -1) player.getInventory().addItem(item);
        else player.getWorld().dropItemNaturally(player.getLocation(), item);
    }

    public boolean hasItem(@NotNull Player player, ItemStack item, int amount){
        PlayerInventory inventory = player.getInventory();
        for(int i = 0; i < inventory.getSize(); i ++){
            ItemStack it = inventory.getItem(i);
            if(it != null && it.getType() == item.getType()){
                amount -= it.getAmount();
            }
        }
        return amount <= 0;
    }

    public boolean takeItem(@NotNull Player player, ItemStack item, int amount){
        PlayerInventory inventory = player.getInventory();

        while(amount > 0){
            boolean found = false;
            for(int i = 0; i < inventory.getSize(); i++){
                ItemStack itm = inventory.getItem(i);
                if(itm != null && itm.getType().equals(item.getType())){
                    found = true;
                    int amt = itm.getAmount()-1;
                    amount -= 1;
                    itm.setAmount(amt);
                    inventory.setItem(i, amt > 0 ? itm : new ItemStack(Material.AIR));
                    player.updateInventory();
                    break;
                }
            }
            if(!found){
                return false;
            }
        }
        return true;
    }

}
