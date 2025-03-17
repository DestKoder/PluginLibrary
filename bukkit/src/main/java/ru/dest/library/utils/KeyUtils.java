package ru.dest.library.utils;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.RegistryKey;

public class KeyUtils {

    @Contract("_ -> new")
    public static @NotNull NamespacedKey key(@NotNull RegistryKey key){
        return key(key.getNamespace(),key.getId());
    }

    @Contract("_, _ -> new")
    @SuppressWarnings("deprecation")
    public static @NotNull NamespacedKey key(String namespace, String id){
        return new NamespacedKey(namespace, id);
    }
}
