package ru.dest.library.bridge;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.Library;
import ru.dest.library.plugin.IPlugin;

public abstract class Bridge<PLUGIN extends IPlugin<?>> {

    protected final Gson gson;

    @Getter
    protected final PLUGIN plugin;
    @Getter
    protected final String channel;

    public Bridge(PLUGIN plugin){
        this.gson = Library.builder.create();
        this.plugin = plugin;

        if(!getClass().isAnnotationPresent(Channel.class)){
            throw new IllegalStateException("Unknown bridge channel! Please add @Channel annotation to bridge class");
        }

        this.channel = getClass().getAnnotation(Channel.class).value();
    }

    public void send(@NotNull Object player, @NotNull String action, @Nullable JsonElement el) {
        JsonObject o = new JsonObject();
        o.addProperty("action", action);
        if(el != null){
            o.add("data", el);
        }

        this.send(player, gson.toJson(o));
    }

    public abstract void send(@NotNull Object player, @NotNull String s);

    public abstract void read(@NotNull Object player, @NotNull String action, @Nullable JsonElement value);

}
