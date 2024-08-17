package ru.dest.library.object;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.events.Namespace;
import java.util.Objects;

/**
 * Class representing a registration key
 *
 * @since 1.0
 * @author DestKoder
 */
@Getter
public class RegistryKey {

    private final String namespace;
    private final String id;

    public RegistryKey(String namespace, String id) {
        this.namespace = namespace;
        this.id = id;
    }


    public String toString(){return  namespace + ":" + id;}

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull RegistryKey minecraft(String id){
        return new RegistryKey("minecraft", id);
    }

    @Contract(pure = true)
    public static @NotNull RegistryKey fromString(@NotNull String s){
        String[] split = s.split(":");

        return new RegistryKey(split[0], split[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistryKey that = (RegistryKey) o;
        return Objects.equals(namespace, that.namespace) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, id);
    }
}
