package ru.dest.library.object;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.utils.RandomUtils;

/**
 * Represents 2D boundary
 */
@Data
public class Boundary {

    private int minX;
    private int maxX;
    private int minZ;
    private int maxZ;

    /**
     * Create a boundary by 4 coordinates
     * @param firstX first x
     * @param secondX second x
     * @param firstZ first z
     * @param secondZ second z
     */
    public Boundary(int firstX, int secondX, int firstZ, int secondZ){
        this.minX = Math.min(firstX, secondX);
        this.maxX = Math.max(firstX, secondX);
        this.minZ = Math.min(firstZ, secondZ);
        this.maxZ = Math.max(firstZ, secondZ);
    }

    /**
     * Create a boundary by 2 points
     * @param first first point
     * @param second second point
     */
    public Boundary(@NotNull Point2D first, @NotNull Point2D second){
        this(first.getX(), second.getX(), first.getZ(), second.getZ());
    }

    /**
     * Check if point is in boundary
     * @param x x coordinate of point
     * @param z z coordinate of point
     * @return true if in or false in other ways
     */
    public boolean in(int x, int z){
        return minX <= x && x <= maxX && minZ <= z && z <= maxZ;
    }
    /**
     * Check if point is in boundary
     * @param point point to check
     * @return true if in or false in other ways
     */
    public boolean in(@NotNull Point2D point){
        return in(point.getX(), point.getZ());
    }

    /**
     * Get random point in this boundary
     */
    public Point2D randomPoint(){
        return new Point2D(
                RandomUtils.randomIntIncludeMax(minX, maxX),
                RandomUtils.randomIntIncludeMax(minZ, maxZ)
        );
    }
}
