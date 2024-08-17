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

public final class Lang {

    private static ComponentSerializer<? extends Component, ? extends Component, String> serializer;

    private final Map<String, String> localization;

    public Lang(Map<String,String> localization) {
        this.localization = localization;
    }

    @NotNull
    public String getValue(@NotNull String key,@NotNull String def){
        return localization.getOrDefault(key, def);
    }

    @NotNull
    public String getValue(@NotNull String key){
        return localization.getOrDefault(key, key);
    }

    public Message getMessage(String key){
        return new ComponentMessage(getValue(key));
    }

    public static void registerSerializer(SerializerType type){
        if(serializer != null )throw new IllegalStateException("Serializer already initialized");
        serializer = type.get();
    }

    public static ComponentSerializer<? extends Component, ? extends Component, String> serializer() { return serializer; }

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
