package ru.dest.library.object;

import lombok.*;

import java.util.Objects;

/**
 * Some point in 3D (x+y+z
 */
@Getter
@Setter
@ToString
public class Point3D extends Point2D{

    private int y;

    public Point3D(int x, int y, int z) {
        super(x, z);
        this.y = y;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Point3D point3D = (Point3D) object;
        return y == point3D.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), y);
    }
}
