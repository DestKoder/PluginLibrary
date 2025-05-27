package ru.dest.library.module.papi;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CustomPlaceholders<T extends JavaPlugin> {

    private final T plugin;

    private final Map<String, Placeholder<T>> placeholders = new HashMap<>();

    public CustomPlaceholders(T plugin) {
        this.plugin = plugin;
    }

    public void register(String placeholderId, Placeholder<T> placeholder){
        this.placeholders.put(placeholderId, placeholder);
    }

    public String getResult(Player player, @NotNull String params){
        String[] data = params.split("_");
        String placeholder = data[0];
        String[] args = new String[data.length -1];
        System.arraycopy(data, 1, args, 0, data.length - 1);

        if(placeholders.containsKey(placeholder)){
            return placeholders.get(placeholder).result(plugin, player, args);
        }
        return null;
    }

    public interface Placeholder<PLUGIN extends JavaPlugin> {
        String result(PLUGIN plugin, Player player, String[] args);
    }
}
