package ru.dest.library.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.permission.PermissionSubject;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import ru.dest.library.lang.impl.ComponentMessage;
import ru.dest.library.lang.impl.ComponentTitle;
import ru.dest.library.object.ISendAble;
import ru.dest.library.plugin.PlatformMethods;
import ru.dest.library.utils.Utils;

import java.util.List;

public class VelocityMethods implements PlatformMethods {

    private final VelocityMain plugin;


    public VelocityMethods(VelocityMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public void send(Object receiver, ISendAble message) {
        if(!(receiver instanceof CommandSource)) throw new IllegalArgumentException("Receiver must be a Player or Console");

        if(message instanceof ComponentMessage) {
            ((CommandSource)receiver).sendMessage(((ComponentMessage)message).build());
        }

        if(message instanceof ComponentTitle title){
            ((CommandSource)receiver).showTitle(Title.title(title.getTitle(), title.getSubtitle()));
        }
    }

    @Override
    public void broadcast(ISendAble message) {
        plugin.getServer().getAllPlayers().forEach(p -> send(p, message));
    }

    @Override
    public void broadcast(ISendAble message, String permission) {
        plugin.getServer().getAllPlayers().forEach(p -> Utils.executeIf(p, pl -> send(pl, message), pl -> pl.hasPermission(permission)));
    }

    @Override
    public List<String> getOnlinePlayerNames() {
        return Utils.list(plugin.getServer().getAllPlayers(), Player::getUsername);
    }

    @Override
    public Object getOnlinePlayer(String name) {
        return plugin.getServer().getPlayer(name).orElse(null);
    }

    @Override
    public Object getOfflinePlayer(String name) {
        return getOnlinePlayer(name);
    }

    @Override
    public boolean checkPermission(Object o, String perm) {
        if(!(o instanceof PermissionSubject)) throw new IllegalArgumentException("Object must be a PermissionSubject instance for permission check");
        return ((PermissionSubject)o).hasPermission(perm);
    }

    @Override
    public void kick(Object player, Component component) {
        if(!(player instanceof Player)) throw new IllegalArgumentException("Object must be a player");
        ((Player) player).disconnect(component);
    }
}
