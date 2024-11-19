package ru.dest.library.registry;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.bukkit.LibraryMain;
import ru.dest.library.exception.ContentAlreadyRegisteredException;
import ru.dest.library.item.Item;
import ru.dest.library.object.RegistryKey;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ItemsRegistry{
    protected final Map<RegistryKey, Item> content = new HashMap<>();
    private static ItemsRegistry instance;

    private ItemsRegistry(){
        instance = this;
    }

    public static ItemsRegistry current() {
        return instance == null ? new ItemsRegistry() : instance;
    }

    public <T extends Item> T register(@NotNull Supplier<T> obj){
        T object = obj.get();
        if(content.containsKey(object.getId())){
            throw new ContentAlreadyRegisteredException(object.getId());
        }
        content.put(object.getId(), object);

        return object;
    }

    public boolean isRegistered(RegistryKey id){
        return content.containsKey(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends Item> T byId(RegistryKey id){
        return (T)content.get(id);
    }

    public Set<RegistryKey> getRegisteredIds(){
        return Collections.unmodifiableSet(content.keySet());
    }

    public Collection<Item> getRegisteredContent(){
        return Collections.unmodifiableCollection(content.values());
    }

    @SuppressWarnings("unchecked")
    public <T extends Item> Collection<T> getRegisteredContent(Predicate<Item> filter){
        List<T> result = new ArrayList<>();
        content.values().forEach(obj -> {
            if(filter.test(obj)) result.add((T)obj);
        });
        return result;
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
