package ru.dest.library.module.papi;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.module.Module;

public class PapiModule implements Module {

    private PapiModule(){}

    public static @Nullable PapiModule init(@NotNull Server server){
        if(server.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            return new PapiModule();
        }
        return null;
    }

    public String applyPlaceholders(Player player, String s){
        return PlaceholderAPI.setPlaceholders(player, s);
    }

    public <T extends JavaPlugin>  void register(CustomPlaceholders<T> config){
        new PapiExpansion(config).register();
    }
}
