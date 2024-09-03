package ru.dest.library.module.economy;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerPointsEconomy implements EconomyModule{

    private final PlayerPointsAPI api;

    private PlayerPointsEconomy(PlayerPointsAPI api) {
        this.api = api;
    }

    public static @Nullable PlayerPointsEconomy init(@NotNull Server server){
        if(server.getPluginManager().isPluginEnabled("PlayerPoints")){
            return new PlayerPointsEconomy(PlayerPoints.getInstance().getAPI());
        }
        return null;
    }

    @Override
    public boolean give(@NotNull OfflinePlayer player, int amount) {
        return api.give(player.getUniqueId(), amount);
    }

    @Override
    public boolean take(@NotNull OfflinePlayer player, int amount) {
        return api.take(player.getUniqueId(), amount);
    }

    @Override
    public boolean has(@NotNull OfflinePlayer player, int amount) {
        return api.look(player.getUniqueId()) >= amount;
    }

    @Override
    public int getBalance(@NotNull OfflinePlayer player) {
        return api.look(player.getUniqueId());
    }
}

