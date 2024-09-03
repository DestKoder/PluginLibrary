package ru.dest.library.module.permission;

import net.milkbowl.vault.permission.Permission;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class VaultPermissions implements PermissionModule{

    private final Permission api;

    private VaultPermissions(Permission api) {
        this.api = api;
    }

    public static VaultPermissions init(Server server){
        if(!server.getPluginManager().isPluginEnabled("Vault")) return null;
        RegisteredServiceProvider<Permission> provider = server.getServicesManager().getRegistration(Permission.class);
        if(provider == null) return null;

        return new VaultPermissions(provider.getProvider());
    }

    @Override
    public boolean hasPermission(UUID player, String perm) {
        return api.playerHas(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(player), perm);
    }

    @Override
    public boolean isInGroup(UUID player, String group) {
        return api.playerInGroup(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(player), group);
    }

    @Override
    public void addToGroupSync(UUID player, String group) {
        api.playerAddGroup(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(player), group);
    }

    @Override
    public void removeFromGroupSync(UUID player, String group) {
        api.playerRemoveGroup(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(player), group);
    }

    @Override
    public String getPrimaryGroup(UUID player) {
        return api.getPrimaryGroup(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(player));
    }

    @Override
    public Set<String> getGroups(UUID player) {
        return Arrays.stream(api.getPlayerGroups(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(player))).collect(Collectors.toSet());
    }

    @Override
    public CompletableFuture<Void> addToGroupAsync(UUID player, String group) {
        throw new NotImplementedException();
    }

    @Override
    public CompletableFuture<Void> removeFromGroupAsync(UUID player, String group) {
        throw new NotImplementedException();
    }

    @Override
    public CompletableFuture<Boolean> hasPermissionAsync(UUID player, String perm) {
        throw new NotImplementedException();
    }

    @Override
    public CompletableFuture<Boolean> isInGroupAsync(UUID player, String perm) {
        throw new NotImplementedException();
    }

    @Override
    public CompletableFuture<Set<String>> getGroupsAsync(UUID player) {
        throw new NotImplementedException();
    }
}
