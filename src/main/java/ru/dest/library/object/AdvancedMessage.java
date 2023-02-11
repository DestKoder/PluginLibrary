package ru.dest.library.object;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.dest.library.utils.Utils;

import static ru.dest.library.utils.ChatUtils.sendMessage;

/**
 * This class represent a message with custom click & hover events
 *
 * @since 1.0
 * @author DestKoder
 */
public class AdvancedMessage {
    private TextComponent message;

    public AdvancedMessage(TextComponent message) {
        this.message = message;
    }

    /**
     * Set click action to this message
     * @param action - click action
     * @param value - Value for click action
     * @return this;
     */
    public AdvancedMessage setClickEvent(ClickEvent.Action action, String value ){
        message.setClickEvent(new ClickEvent(action, value));
        return this;
    }

    /**
     * Set hover event to this message
     * @param action - hover action
     * @param value - Value for hover action
     * @return this;
     */
    public AdvancedMessage setHoverEvent(HoverEvent.Action action, BaseComponent[] value){
        message.setHoverEvent(new HoverEvent(action, value));
        return this;
    }

    /**
     * @return {@link TextComponent} which stored message;
     */
    public TextComponent getMessageContent() {
        return message;
    }

    /**
     * Add additional message to this message.
     * @param message - {@link AdvancedMessage} to add;
     * @return this
     */
    public AdvancedMessage add(AdvancedMessage message){
        this.message.addExtra(message.getMessageContent());

        return this;
    }

    /**
     * Send's this message to a player
     * @param player {@link Player} to send for
     */
    public void send(Player player){
        sendMessage(player,message);
    }

    /**
     * Send's message to player with specified {@link ChatMessageType}
     * @param player {@link Player} to send for
     * @param position {@link ChatMessageType}
     */
    public void send(Player player, ChatMessageType position){
        sendMessage(player,message, position);
    }

    /**
     * Broadcast this message to all online players
     */
    public void broadcast() {
        Bukkit.getOnlinePlayers().forEach(p -> sendMessage(p, message));
    }

    /**
     * Broadcast this message to all online players with specified {@link ChatMessageType}
     *
     * @param position {@link ChatMessageType}
     */
    public void broadcast(ChatMessageType position){
        Bukkit.getOnlinePlayers().forEach(p -> sendMessage(p, message, position));
    }


    /**
     * Broadcast message to all online players with permission check
     * @param permission Permission to check
     */
    public void broadcast(String permission){
        Bukkit.getOnlinePlayers().forEach(p -> Utils.executeIfHas(p, permission, this::send));
    }

    /**
     * Broadcast message to all online players with permission check and specified {@link ChatMessageType}
     * @param permission Permission to check
     * @param position {@link ChatMessageType}
     */
    public void broadcast(ChatMessageType position, String permission){
        Bukkit.getOnlinePlayers().forEach(p -> Utils.executeIfHas(p, permission, pl -> send(pl, position)));
    }
}
