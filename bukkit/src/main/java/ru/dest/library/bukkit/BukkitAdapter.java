package ru.dest.library.bukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Utility;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.Point3D;
import ru.dest.library.object.RegistryKey;

@UtilityClass
public class BukkitAdapter {

    public NamespacedKey adapt(@NotNull RegistryKey key){
        return new NamespacedKey(key.getNamespace(), key.getId());
    }

    public RegistryKey adapt(@NotNull NamespacedKey key){
        return new RegistryKey(key.getNamespace(), key.getKey());
    }

    public Location adapt(World world, @NotNull Point3D point) {
        return new Location(world, point.getX(), point.getY(), point.getZ());
    }

    public Point3D adapt(@NotNull Location location){
        return new Point3D(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
