package ru.dest.library.bukkit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.bridge.Bridge;

import ru.dest.library.plugin.MinecraftPlugin;

import java.nio.charset.StandardCharsets;

public abstract class BukkitBridge<PLUGIN extends MinecraftPlugin<PLUGIN, ?>> extends Bridge<PLUGIN> implements PluginMessageListener {

    public BukkitBridge(PLUGIN plugin) {
        super(plugin);

        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, getChannel(), this);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, getChannel());
    }

    @Override
    public void send(@NotNull Object player, @NotNull String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        if(Bukkit.getOnlinePlayers().isEmpty()) {
            plugin.logger().warning("Couldn't send plugin message. Online < 1");
            return;
        }
        plugin.getServer().sendPluginMessage(plugin, channel, bytes);
    }

    @Override
    public void onPluginMessageReceived(@NotNull String s, @NotNull Player player, byte @NotNull [] bytes) {
        if(!s.equalsIgnoreCase(channel)) return;

        String data = new String(bytes, StandardCharsets.UTF_8);
        JsonObject obj = new JsonParser().parse(data).getAsJsonObject();

        String action = obj.get("action").getAsString();
        JsonElement dataObj = obj.has("data") ? obj.get("data") : null;

        this.read(player, action, dataObj);
    }
}
