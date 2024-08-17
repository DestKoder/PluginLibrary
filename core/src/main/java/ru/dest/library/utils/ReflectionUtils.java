package ru.dest.library.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.FieldType;

import java.lang.reflect.Field;
import java.util.List;

@UtilityClass
public final class ReflectionUtils {

    public static void setValue(@NotNull Field f, Object parent, Object value) throws IllegalAccessException {
        boolean accessible = f.isAccessible();
        if(!accessible) f.setAccessible(true);
        f.set(parent, value);
        f.setAccessible(accessible);
    }

    public static Object getValue(@NotNull Field f, Object parent) throws IllegalAccessException {
        boolean accessible = f.isAccessible();
        if(!accessible) f.setAccessible(true);
        Object val = f.get(parent);
        f.setAccessible(accessible);
        return val;
    }

    public static FieldType getFieldType(@NotNull Field f){
        Class<?> cl = f.getType();

        if(cl.getName().equalsIgnoreCase("int") || cl.equals(Integer.class)) return FieldType.INTEGER;
        if(cl.getName().equalsIgnoreCase("double") || cl.equals(Double.class)) return FieldType.DOUBLE;
        if(cl.getName().equalsIgnoreCase("float") || cl.equals(Float.class)) return FieldType.FLOAT;
        if(cl.getName().equalsIgnoreCase("boolean") || cl.equals(Boolean.class)) return FieldType.BOOLEAN;
        if(cl.getName().equalsIgnoreCase("long") || cl.equals(Long.class)) return FieldType.LONG;
        if(cl.isEnum()) return FieldType.ENUM;
        if(cl.equals(List.class)) return FieldType.LIST;
        if(cl.equals(String.class)) return FieldType.STRING;
        return FieldType.UNKNOWN;
    }

    public static Object callMethod(@NotNull Class<?> cl, String name, Class<?>[] args, Object o, Object[] values) throws Exception {
        return cl.getMethod(name, args).invoke(o, values);
    }

}
