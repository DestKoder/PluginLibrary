package ru.dest.library.velocity;


import com.google.gson.JsonElement;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.jetbrains.annotations.NotNull;

import ru.dest.library.bridge.Bridge;
import ru.dest.library.plugin.MinecraftPlugin;

import java.nio.charset.StandardCharsets;

public abstract class VelocityBridge<PLUGIN extends MinecraftPlugin<PLUGIN, ?>> extends Bridge<PLUGIN> {

    private final ChannelIdentifier identifier;

    public VelocityBridge(PLUGIN plugin) {
        super(plugin);

        String[] channelInfo = getChannel().split(":", 2);
        this.identifier = MinecraftChannelIdentifier.create(channelInfo[0], channelInfo[1]);

        plugin.getServer().getChannelRegistrar().register(identifier);
        plugin.getServer().getEventManager().register(plugin, this);
    }

    @Override
    public void send(@NotNull Object player, @NotNull String s) {
        if(!(player instanceof Player)) throw new IllegalArgumentException("Object must be a Player");
        byte[] response = s.getBytes(StandardCharsets.UTF_8);

        ((Player)player).sendPluginMessage(identifier, response);
    }

    @Subscribe
    public void onMessageReceived(@NotNull PluginMessageEvent event){
        if (!event.getIdentifier().equals(identifier)) return;

        // Read message from Bukkit
        String data = new String(event.getData(), StandardCharsets.UTF_8);

        JsonObject obj = new JsonParser().parse(data).getAsJsonObject();

        String action = obj.get("action").getAsString();
        JsonElement dataObj = obj.has("data") ? obj.get("data") : null;

        this.read(event.getSource(), action, dataObj);
    }


}
