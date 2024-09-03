package ru.dest.library.module.permission;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Server;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LuckPermsPermissions implements PermissionModule{

    private final LuckPerms api;

    private LuckPermsPermissions(LuckPerms api) {
        this.api = api;
    }

    public static LuckPermsPermissions init(Server server){
        if(!server.getPluginManager().isPluginEnabled("LuckPerms")) return null;

        return new LuckPermsPermissions(LuckPermsProvider.get());
    }

    public User giveMeADamnUser(UUID uniqueId) {
        UserManager userManager = api.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(uniqueId);

        return userFuture.join(); // ouch! (block until the User is loaded)
    }

    @Override
    public boolean hasPermission(UUID player, String perm) {
        return hasPermissionAsync(player,perm).join();
    }

    @Override
    public String getPrimaryGroup(UUID player) {
        return giveMeADamnUser(player).getPrimaryGroup();
    }

    @Override
    public Set<String> getGroups(UUID player) {
        return getGroupsAsync(player).join();
    }

    @Override
    public boolean isInGroup(UUID player, String group) {
        return isInGroupAsync(player, group).join();
    }

    @Override
    public CompletableFuture<Void> addToGroupAsync(UUID player, String group) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        api.getUserManager().loadUser(player).thenAccept(u -> {
            u.data().add(InheritanceNode.builder().group(group).build());
            future.complete(null);
        });

        return future;
    }

    @Override
    public CompletableFuture<Void> removeFromGroupAsync(UUID player, String group) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        api.getUserManager().loadUser(player).thenAccept(u-> {
            u.data().remove(InheritanceNode.builder().group(group).build());
            future.complete(null);
        });
        return future;
    }

    @Override
    public CompletableFuture<Boolean> hasPermissionAsync(UUID player, String perm) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        api.getUserManager().loadUser(player).thenAccept(u -> {
            future.complete(u.getNodes(NodeType.PERMISSION).stream().anyMatch(node -> !node.isNegated()&&!node.hasExpired()&&node.getPermission().equals(perm)));
        });

        return future;
    }

    @Override
    public CompletableFuture<Boolean> isInGroupAsync(UUID player, String group) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        api.getUserManager().loadUser(player).thenAccept(u -> {
            future.complete(u.getNodes(NodeType.INHERITANCE).stream().anyMatch(node -> !node.isNegated()&&!node.hasExpired()&&node.getGroupName().equals(group)));
        });

        return future;
    }

    @Override
    public CompletableFuture<Set<String>> getGroupsAsync(UUID player) {
        CompletableFuture<Set<String>> future = new CompletableFuture<>();

        api.getUserManager().loadUser(player).thenAccept(u -> {
             future.complete(u.getNodes(NodeType.INHERITANCE).stream().map(InheritanceNode::getGroupName).collect(Collectors.toSet()));
        });

        return future;
    }

    @Override
    public void addToGroupSync(UUID player, String group) {
        addToGroupAsync(player, group).join();
    }

    @Override
    public void removeFromGroupSync(UUID player, String group) {
        removeFromGroupAsync(player, group).join();
    }
}
