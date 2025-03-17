package ru.dest.library.object;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FormatPair extends Pair<String, Object>{

    private static final Map<Class<?>, Mapper<?>> MAPPERS = new HashMap<>();

    public static <T> void regMapper(Class<T> cl, Mapper<T> mapper){
        if(MAPPERS.containsKey(cl)) throw new IllegalStateException("Mapper for this class already registered");
        MAPPERS.put(cl, mapper);
    }

    private static Object map(@NotNull Object o){
        if(MAPPERS.containsKey(o.getClass())){
            return MAPPERS.get(o.getClass()).mapObj(o);
        }
        return o;
    }

    public FormatPair(String firstValue, Object secondValue) {
        super(firstValue, map(secondValue));
    }

    @Contract("_, _ -> new")
    public static @NotNull FormatPair of(String key, Object val){
        return new FormatPair(key, val);
    }

    public interface Mapper<T> {
        String map(T t);

        default String mapObj(Object o){
            return map((T)o);
        }
    }
}
