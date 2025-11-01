package ru.dest.library.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class ItemProviders {

    private static final List<Provider> providers = new ArrayList<>();

    public static void register(Provider provider){
        providers.add(provider);
    }

    @Nullable
    public static ItemStack find(@NotNull NamespacedKey id){
        ItemStack item = null;

        for(Provider p : providers){
            ItemStack i = p.find(id);
            if(i != null){
                item = i;
                break;
            }
        }
        return item;
    }


    @FunctionalInterface
    public interface Provider {
        @Nullable
        ItemStack find(@NotNull NamespacedKey id);
    }
}
