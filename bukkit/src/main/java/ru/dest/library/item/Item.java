package ru.dest.library.item;


import com.google.gson.JsonObject;
import lombok.Getter;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.bukkit.LibraryMain;
import ru.dest.library.event.ItemInteractEvent;
import ru.dest.library.item.behavior.ItemBehavior;
import ru.dest.library.object.RegistryKey;
import ru.dest.library.registry.IRegistrable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item implements IItem, IRegistrable {

    private final ItemProps properties;
    private final String name;
    private final List<String> lore;
    @Getter
    private final RegistryKey id;

    private ItemStack item;

    public Item(RegistryKey id, ItemProps properties, @Nullable String name, @Nullable List<String> lore) {
        this.properties = properties;
        this.name = name == null ? "item."+id.getId()+".name" : name;
        this.lore = lore == null ? new ArrayList<>() : lore;
        this.lore.add("&r");
        this.id = id;
    }

    public Item(RegistryKey id, ItemProps props){
        this(id, props, null, null);
    }


    public void onInteract(@NotNull ItemInteractEvent event){
        return;
    }

    public void onDrop(PlayerDropItemEvent event){
        return;
    }

    public void onDamage(PlayerItemDamageEvent event){
        return;
    }

    public final String getName() {
        return name;
    }

    public final int getMaxStackSize(){
        return properties.getMaxStackSize();
    }

    public JsonObject toJson(JsonObject object){
        return object;
    }

    public ItemStack fromJson(ItemStack item, JsonObject object){
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item1 = (Item) o;
        return Objects.equals(id, item1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties, name, lore, id, item);
    }

    @Override
    public String toString() {
        return "Item{" +
                "properties=" + properties +
                ", name='" + name + '\'' +
                ", lore=" + lore +
                ", id=" + id +
                ", item=" + item +
                '}';
    }

    public ItemStack get(){
        if(item!=null) return item.clone();
        ItemStack item = new ItemStack(properties.getMaterial(), 1);
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(LibraryMain.getInstance().getItemId(), PersistentDataType.STRING, id.toString());

        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setCustomModelData(properties.getModel());

        item.setItemMeta(meta);

        this.item=item;
        if(this instanceof ItemBehavior) ((ItemBehavior)this).onItemCreate(meta);

        return get();
    }
}
