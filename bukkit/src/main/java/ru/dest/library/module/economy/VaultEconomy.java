package ru.dest.library.module.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VaultEconomy implements EconomyModule{

    private final Economy economy;

    public VaultEconomy(Economy economy) {
        this.economy = economy;
    }

    public static @Nullable VaultEconomy init(@NotNull Server server){
        if(!server.getPluginManager().isPluginEnabled("Vault")) return null;
        RegisteredServiceProvider<Economy> economyProvider = server.getServicesManager().getRegistration(Economy.class);

        if(economyProvider == null) return null;

        return new VaultEconomy(economyProvider.getProvider());
    }

    @Override
    public boolean give(OfflinePlayer player, int amount) {
        return economy.depositPlayer(player, amount).transactionSuccess();
    }

    @Override
    public boolean take(OfflinePlayer player, int amount) {
        return economy.withdrawPlayer(player ,amount).transactionSuccess();
    }

    @Override
    public boolean has(OfflinePlayer player, int amount) {
        return economy.getBalance(player) >= amount;
    }

    @Override
    public int getBalance(OfflinePlayer player) {
        return (int)economy.getBalance(player);
    }
}
