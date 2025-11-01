package ru.dest.library.lang;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.config.ConfigWorker;
import ru.dest.library.config.DataConfig;
import ru.dest.library.lang.impl.ComponentMessage;
import ru.dest.library.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Key to Translation map
 */
public final class Lang {

    private static ComponentSerializer<? extends Component, ? extends Component, String> serializer;

    private final Map<String, String> localization;

    public Lang(Map<String,String> localization) {
        this.localization = localization;
    }

    /**
     * Get string value of key
     * @param key key to search
     * @param def default value if no value for key found
     * @return key value if found or default value in other cases
     */
    @NotNull
    public String getValue(@NotNull String key,@NotNull String def){
        return localization.getOrDefault(key, def);
    }

    /**
     * Get message value
     * @param key key to search
     * @return key value if found or key if not
     */
    @NotNull
    public String getValue(@NotNull String key){
        return localization.getOrDefault(key, key);
    }

    /**
     * Convert {@link String} to {@link Message}
     * @param s string with message
     * @return {@link Message} equivalent of given string
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Message make(String s){
        return new ComponentMessage(s);
    }

    /**
     * Get localization;
     * @param key key to search
     * @return {@link Message}
     */
    @Contract("_ -> new")
    public @NotNull Message getMessage(String key){
        return make(getValue(key));
    }

    public static void registerSerializer(SerializerType type){
        if(serializer != null )throw new IllegalStateException("Serializer already initialized");
        serializer = type.get();
    }

    /**
     * Current component serializer used in library & it's plugins
     */
    public static ComponentSerializer<? extends Component, ? extends Component, String> serializer() { return serializer; }

    /**
     * Load localization from file
     * @param f file to load
     * @return {@link Lang} loaded from file
     * @throws IOException if error occupied while reading a file
     */
    @Contract("_ -> new")
    public static @NotNull Lang load(File f) throws IOException {
        DataConfig cfg = ConfigWorker.load(f);

        Map<String, String> localization = new HashMap<>();
        for(String s : cfg.getKeys()){
            Object o = cfg.get(s , s);
            if(o instanceof List){
                List<String> l = (List<String>)o;
                localization.put(s, Utils.collectionToString(l, "\n"));
            }else {
                String str = o.toString();
                localization.put(s, str);
            }
        }

        return new Lang(localization);
    }

}
