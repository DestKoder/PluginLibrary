package ru.dest.library.command.argument;

import lombok.experimental.UtilityClass;
import ru.dest.library.utils.Patterns;
import ru.dest.library.utils.TimeUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.dest.library.utils.Utils.list;

@UtilityClass
public class ArgumentTypes {
    private final Map<Class<?>, IArgumentType> types = new HashMap<>();

    public final class IP_V4 implements IArgumentType{
        @Override
        public boolean isValid(String arg) {
            return arg.matches(Patterns.IP_V4);
        }

        @Override
        public List<String> getCompletions() {
            return list("127.0.0.1", "127.0.0.2");
        }
    }

    public void register(Class<?> cl, IArgumentType type){
        types.put(cl, type);
    }

    public boolean check(Class<?> cl, String arg){
        if(!types.containsKey(cl)) throw new IllegalArgumentException(cl + " is not registered as argument type");
        return types.get(cl).isValid(arg);
    }

    public List<String> getCompletions(Class<?> cl){
        if(!types.containsKey(cl)) throw new IllegalArgumentException(cl + " is not registered as argument type");
        return types.get(cl).getCompletions();

    }

    static {
        register(String.class, new IArgumentType() {
            @Override
            public boolean isValid(String arg) {
                return true;
            }

            @Override
            public List<String> getCompletions() {
                return list("SomeTextHere");
            }
        });
        register(Integer.class, new IArgumentType() {
            @Override
            public boolean isValid(String arg) {
                return arg.matches(Patterns.INTEGER);
            }

            @Override
            public List<String> getCompletions() {
                return list("1", "2", "10", "100", "500");
            }
        });
        register(Double.class, new IArgumentType() {
            @Override
            public boolean isValid(String arg) {
                return arg.matches(Patterns.DOUBLE);
            }

            @Override
            public List<String> getCompletions() {
                return list("1.0", "12.0", "15.4");
            }
        });
        register(Boolean.class, new IArgumentType() {
            @Override
            public boolean isValid(String arg) {
                return arg.matches(Patterns.BOOLEAN);
            }

            @Override
            public List<String> getCompletions() {
                return list("true", "false");
            }
        });
        register(IP_V4.class, new IP_V4());
        register(TimeUnit.class, new IArgumentType() {
            @Override
            public boolean isValid(String arg) {
                return arg.matches(Patterns.TIME_UNIT);
            }

            @Override
            public List<String> getCompletions() {
                return list("1s", "1m", "1h", "1d", "1M", "1w", "1y");
            }
        });
    }

}
