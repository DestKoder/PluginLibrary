package ru.dest.library.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.Boundary;

@UtilityClass
public final class BukkitUtils {

    public @NotNull Boundary getWorldBorder(@NotNull World world){
        int centerX = world.getWorldBorder().getCenter().getBlockX(),
                centerZ = world.getWorldBorder().getCenter().getBlockZ();

        return new Boundary(
                centerX - (int)(world.getWorldBorder().getSize()/2),
                centerX + (int)(world.getWorldBorder().getSize()/2),
                centerZ - (int)(world.getWorldBorder().getSize()/2),
                centerZ + (int)(world.getWorldBorder().getSize()/2)
        );
    }
}
