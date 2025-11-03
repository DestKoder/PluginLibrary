package ru.dest.library.pdc;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class KeyDataType implements PersistentDataType<String, NamespacedKey> {

    @NotNull
    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @NotNull
    @Override
    public Class<NamespacedKey> getComplexType() {
        return NamespacedKey.class;
    }

    @NotNull
    @Override
    public String toPrimitive(@NotNull NamespacedKey namespacedKey, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        return namespacedKey.toString();
    }

    @NotNull
    @Override
    public NamespacedKey fromPrimitive(@NotNull String s, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        return Objects.requireNonNull(NamespacedKey.fromString(s));
    }
}
