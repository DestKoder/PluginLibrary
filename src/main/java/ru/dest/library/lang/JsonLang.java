package ru.dest.library.lang;

import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.object.Message;
import ru.dest.library.object.Title;
import ru.dest.library.utils.ChatUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JsonLang implements Lang{

    private final Map<String, String> data;

    private JsonLang(Map<String, String> data){
        this.data = data;
    }

    @Override
    public String getRawString(String key) {
        return data.get(key);
    }

    @Override
    public Message getMessage(String key) {
        String msg = getRawString(key);
        return msg == null ? null : new Message(msg);
    }

    @Override
    public Title getTitle(String key) {
        String subtitle = data.get(key+".subtitle"), title = data.get(key + ".title");
        if(title == null) return null;

        return new Title(title,subtitle);
    }

    @Override
    public Message getMessage(String key, Player placeholder) {
        String msg = data.get(key);
        if(msg == null) return null;

        return new Message(ChatUtils.applyPlaceholders(msg, placeholder));
    }

    @Override
    @Nullable
    public Title getTitle(String key, Player placeholder) {
        String subtitle = data.get(key+".subtitle"), title = data.get(key + ".title");
        if(title == null) return null;
        if(subtitle != null) subtitle = ChatUtils.applyPlaceholders(key, placeholder);

        title = ChatUtils.applyPlaceholders(title, placeholder);

        return new Title(title, subtitle);
    }

    @Contract("_ -> new")
    public static @NotNull JsonLang loadFromFile(@NotNull final File f) throws FileNotFoundException, IOException, IllegalArgumentException {
        final Map<String, String> data = new HashMap<>();
        if(!f.exists())throw  new FileNotFoundException("Lang file " + f.getName() + " doesn't exists");
        if(!f.getName().endsWith(".json")) throw new IllegalArgumentException("Given file must be a .json file!");

        JsonObject element = new JsonParser().parse(FileUtils.readFileToString(f, StandardCharsets.UTF_8)).getAsJsonObject();

        element.entrySet().forEach(e -> {
            data.put(e.getKey(), e.getValue().getAsString());
        });

        return new JsonLang(data);
    }
}
