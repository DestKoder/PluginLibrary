package ru.dest.library.plugin;

import ru.dest.library.lang.Message;
import ru.dest.library.object.ISendAble;

import java.util.List;

public interface PlatformMethods {
    void send(Object receiver, ISendAble message);
    void broadcast(ISendAble message);
    void broadcast(ISendAble message, String permission);

    List<String> getOnlinePlayerNames();

    Object getOnlinePlayer(String name);
    Object getOfflinePlayer(String name);

    boolean checkPermission(Object o, String perm);
}
