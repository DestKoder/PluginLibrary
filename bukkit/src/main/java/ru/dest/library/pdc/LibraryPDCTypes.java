package ru.dest.library.pdc;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public final class LibraryPDCTypes {

    public static final PersistentDataType<String, NamespacedKey> NAMESPACED_KEY = new KeyDataType();

}
