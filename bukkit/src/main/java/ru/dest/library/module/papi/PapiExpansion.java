package ru.dest.library.module.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PapiExpansion extends PlaceholderExpansion {

    private final CustomPlaceholders<? extends JavaPlugin> config;

    public PapiExpansion(CustomPlaceholders<? extends JavaPlugin> config) {
        this.config = config;
    }


    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        String result = config.getResult(player, params);

        return result == null ? super.onPlaceholderRequest(player, params) : result;
    }

    @Override
    public @NotNull String getIdentifier() {
        return config.getPlugin().getName().toLowerCase();
    }

    @Override
    public @NotNull String getAuthor() {
        if(!config.getPlugin().getDescription().getAuthors().isEmpty()){
            return config.getPlugin().getDescription().getAuthors().get(0);
        }else {
            return "Unknown";
        }
    }

    @Override
    public @NotNull String getVersion() {
        return config.getPlugin().getDescription().getVersion();
    }
}
