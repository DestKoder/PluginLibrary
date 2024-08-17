package ru.dest.library.registry;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.bukkit.LibraryMain;
import ru.dest.library.item.Item;
import ru.dest.library.object.RegistryKey;

public class ItemsRegistry extends AbstractContentRegistry<Item>{

    private static ItemsRegistry instance;

    private ItemsRegistry(){
        instance = this;
    }

    public static ItemsRegistry current() {
        return instance == null ? new ItemsRegistry() : instance;
    }

    public <T extends Item> T byItemStack(@Nullable ItemStack item){
        if(item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;
        String key = meta.getPersistentDataContainer().get(LibraryMain.getInstance().getItemId(), PersistentDataType.STRING);
        if(key == null) return null;

        RegistryKey id = RegistryKey.fromString(key);

        return byId(id);
    }
}
