package ru.dest.library.object;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FormatPair extends Pair<String, Object>{

    public static final Map<Class<?>, Mapper<?>> MAPPERS = new HashMap<>();

    public static <T> void regMapper(Class<T> cl, Mapper<T> mapper){
        if(MAPPERS.containsKey(cl)) throw new IllegalStateException("Mapper for this class already registered");
        MAPPERS.put(cl, mapper);
    }

    private static Object map(@NotNull Object o){
        Mapper<?> mapper = MAPPERS.get(o.getClass());

        if(mapper == null){
            for(Class<?> i : o.getClass().getInterfaces()){
                if(MAPPERS.containsKey(i)){
                    mapper = MAPPERS.get(i);
                    break;
                }
            }
        }

        return mapper == null ? o : mapper.mapObj(o);
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
