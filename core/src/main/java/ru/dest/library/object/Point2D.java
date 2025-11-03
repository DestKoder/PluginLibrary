package ru.dest.library.object;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * Some point in 2D (x+z)
 */
@AllArgsConstructor
@Data
public class Point2D {

    private int x;
    private int z;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Point2D point2D = (Point2D) object;
        return x == point2D.x && z == point2D.z;
    }

    public Point3D to3D(int y){
        return new Point3D(x,y,z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }
}
