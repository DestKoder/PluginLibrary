package ru.dest.library.bukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.Utility;
import ru.dest.library.object.RegistryKey;

@UtilityClass
public class BukkitAdapter {

    public NamespacedKey adapt(RegistryKey key){
        return new NamespacedKey(key.getNamespace(), key.getId());
    }

    public RegistryKey adapt(NamespacedKey key){
        return new RegistryKey(key.getNamespace(), key.getKey());
    }

}
