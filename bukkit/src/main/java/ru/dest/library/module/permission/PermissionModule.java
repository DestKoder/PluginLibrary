package ru.dest.library.module.permission;

import ru.dest.library.module.Module;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PermissionModule extends Module {

    boolean hasPermission(UUID player, String perm);
    boolean isInGroup(UUID player, String group);


    String getPrimaryGroup(UUID player);
    Set<String> getGroups(UUID player);
    CompletableFuture<Void> addToGroupAsync(UUID player, String group);
    CompletableFuture<Void> removeFromGroupAsync(UUID player, String group);

    CompletableFuture<Boolean> hasPermissionAsync(UUID player, String perm);
    CompletableFuture<Boolean> isInGroupAsync(UUID player, String perm);
    CompletableFuture<Set<String>> getGroupsAsync(UUID player);
    void addToGroupSync(UUID player, String group);
    void removeFromGroupSync(UUID player, String group);

}
