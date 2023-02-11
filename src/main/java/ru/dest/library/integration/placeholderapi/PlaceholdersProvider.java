package ru.dest.library.integration.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class represents a basic PlaceholderExpansion and allows to register placeholder in code
 *
 * @since 1.0
 * @author DestKoder
 */
public class PlaceholdersProvider extends PlaceholderExpansion {

    private final String identifier;
    private final String author;
    private final String version;

    private final Map<String, Function<Player, String>> placeholders = new HashMap<>();

    public PlaceholdersProvider(String identifier, String version, String author) {
        this.identifier = identifier;
        this.author = author;
        this.version = version;
    }

    /**
     * Register a new placeholder for indentifier
     * @param placeholder placeholder for register
     * @param replaceTo function which returns a text on which placeholder will be replaced
     */
    public void registerPlaceholder(String placeholder, Function<Player, String> replaceTo){
        this.placeholders.put(placeholder, replaceTo);
    }

    @Override
    public @NotNull String getIdentifier() {
        return identifier;
    }

    @Override
    public @NotNull String getAuthor() {
        return author;
    }

    @Override
    public @NotNull String getVersion() {
        return version;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if(placeholders.containsKey(params)){
            return placeholders.get(params).apply(player);
        }

        return super.onPlaceholderRequest(player, params);
    }
}
