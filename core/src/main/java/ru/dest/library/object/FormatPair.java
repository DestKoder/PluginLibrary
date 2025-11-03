package ru.dest.library.object;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


/**
 * Represent a format key=value pair
 * with some custom mapper
 * -
 * for example player Object to playerName (String)
 */
public class FormatPair extends Pair<String, Object>{

    public static final Map<Class<?>, Mapper<?>> MAPPERS = new HashMap<>();

    /**
     * Register a custom mapper
     * @param cl Mapper class
     * @param mapper Mapper
     * @param <T> type of mapper
     */
    public static <T> void regMapper(Class<T> cl, Mapper<T> mapper){
        if(MAPPERS.containsKey(cl)) throw new IllegalStateException("Mapper for this class already registered");
        MAPPERS.put(cl, mapper);
    }

    private static Object map(Object o){
        if(o == null) return "null";
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

    /**
     * Create new pair
     * @param firstValue key to replace
     * @param secondValue value to replace on.
     */
    public FormatPair(String firstValue, Object secondValue) {
        super(firstValue, map(secondValue));
    }

    @Contract("_, _ -> new")
    public static @NotNull FormatPair of(String key, Object val){
        return new FormatPair(key, val);
    }

    /**
     * Describes an object to string mapper
     * @param <T>
     */
    public interface Mapper<T> {
        String map(T t);

        default String mapObj(Object o){
            return o == null ? "null" : map((T)o);
        }
    }
}
