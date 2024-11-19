package ru.dest.library.bukkit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import ru.dest.library.lang.impl.ComponentMessage;
import ru.dest.library.lang.impl.ComponentTitle;
import ru.dest.library.object.ISendAble;
import ru.dest.library.plugin.PlatformMethods;
import ru.dest.library.utils.ReflectionUtils;

import java.util.List;

import static ru.dest.library.utils.Utils.executeIf;
import static ru.dest.library.utils.Utils.list;

public final class BukkitMethods implements PlatformMethods {

    @Override
    public void send(Object receiver, ISendAble message) {
        if(!(receiver instanceof CommandSender)){
            throw new IllegalArgumentException("Receiver must be a CommandSender representation");
        }
        if(message instanceof ComponentMessage){
            //Send a component message;
            if(LibraryMain.getInstance().getAudienceProvider() != null){
                if(receiver instanceof Player){
                    LibraryMain.getInstance().getAudienceProvider().player(((Player)receiver).getUniqueId()).sendMessage(((ComponentMessage) message).build());
                    return;
                }
                LibraryMain.getInstance().getAudienceProvider().console().sendMessage(((ComponentMessage) message).build());
                return;
            }
            //Send via a method)
            CommandSender rec = (CommandSender) receiver;

            try {
                ReflectionUtils.callMethod(rec.getClass(), "sendMessage", new Class[]{Component.class}, rec, new Object[]{((ComponentMessage) message).build()});
            } catch (Exception e) {
                LibraryMain.getInstance().logger().warning("Couldn't send message to receiver...");
                LibraryMain.getInstance().logger().error(e);
            }
            return;
        }
        if(message instanceof ComponentTitle) {
            ComponentTitle title = (ComponentTitle) message;
            if(LibraryMain.getInstance().getAudienceProvider() != null){
                if(receiver instanceof Player){
                    LibraryMain.getInstance().getAudienceProvider().player(((Player)receiver).getUniqueId()).showTitle(Title.title(title.getTitle(), title.getSubtitle()));
                    return;
                }
                return;
            }
            if(receiver instanceof Player){
                Player rec = (Player) receiver;
                try {
                    ReflectionUtils.callMethod(rec.getClass(), "showTitle", new Class[]{Title.class}, rec, new Object[]{Title.title(title.getTitle(), title.getSubtitle())});
                } catch (Exception e) {
                    LibraryMain.getInstance().logger().warning("Couldn't send message to receiver...");
                    LibraryMain.getInstance().logger().error(e);
                }
                return;
            }
        }

        throw new IllegalArgumentException("Couldn't save a SendAble with class " + message.getClass() + " to bukkit player");
    }

    @Override
    public void kick(Object player, Component component) {
        if(!(player instanceof  Player)) return;
        if(LibraryMain.getInstance().getAudienceProvider() != null){
            ((Player)player).kickPlayer(component.toString());
            return;
        }

        try {
            ReflectionUtils.callMethod(player.getClass(), "kick", new Class[]{Component.class}, player, new Object[]{component});
        } catch (Exception e) {
            LibraryMain.getInstance().logger().warning("Couldn't kick player...");
            LibraryMain.getInstance().logger().error(e);
        }
        return;
    }

    @Override
    public Object getOnlinePlayer(String name) {
        return Bukkit.getPlayer(name);
    }

    @Override
    public Object getOfflinePlayer(String name) {
        return Bukkit.getOfflinePlayer(name);
    }

    @Override
    public boolean checkPermission(Object o, String perm) {
        if(!(o instanceof Permissible)) throw new IllegalArgumentException("Object must be an instance of  Permissible");
        return ((Permissible)o).hasPermission(perm);
    }

    @Override
    public void broadcast(ISendAble message) {
        Bukkit.getOnlinePlayers().forEach(p-> send(p, message));
    }

    @Override
    public void broadcast(ISendAble message, String permission) {
        Bukkit.getOnlinePlayers().forEach(p -> executeIf(p, pl ->  send(pl, message),  pl -> pl.hasPermission(permission)));
    }

    @Override
    public List<String> getOnlinePlayerNames() {
        return list(Bukkit.getOnlinePlayers(), HumanEntity::getName);
    }
}
