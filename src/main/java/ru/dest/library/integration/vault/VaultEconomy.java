package ru.dest.library.integration.vault;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.economy.EconomyResult;
import ru.dest.library.economy.IEconomy;
import ru.dest.library.exception.IntegrationInitException;

public class VaultEconomy implements IEconomy {

    public static final String ID = "Vault";

    private Economy economy;

    public VaultEconomy(@NotNull Server server) throws IntegrationInitException {
        if(!server.getPluginManager().isPluginEnabled("Vault")) throw new IntegrationInitException("Couldn't init vault economy. Vault not found");

        RegisteredServiceProvider<Economy> rsp = server.getServicesManager().getRegistration(Economy.class);

        if(rsp == null) throw new IntegrationInitException("Couldn't init vault economy. Vault economy isn't setup. Please install Essentials/CMI/Another plugins that provides vault economy");

        this.economy = rsp.getProvider();
    }

    @Override
    @NotNull
    public EconomyResult giveMoney(@NotNull OfflinePlayer player, double amount) {
        EconomyResponse response = economy.depositPlayer(player, amount);
        return new EconomyResult(response.amount, response.balance, response.type == EconomyResponse.ResponseType.SUCCESS ? EconomyResult.ResultType.SUCCESS : EconomyResult.ResultType.FAIL);
    }

    @Override
    @NotNull
    public EconomyResult takeMoney(@NotNull OfflinePlayer player, double amount){
        EconomyResponse r = economy.withdrawPlayer(player, amount);
        return new EconomyResult(r.amount, r.balance, r.type == EconomyResponse.ResponseType.SUCCESS ? EconomyResult.ResultType.SUCCESS : r.type == EconomyResponse.ResponseType.NOT_IMPLEMENTED ? EconomyResult.ResultType.FAIL : r.errorMessage == null ? EconomyResult.ResultType.FAIL : EconomyResult.ResultType.FAIL_NOMONEY);
    }

    @Override
    public double getBalance(OfflinePlayer player){
        return economy.getBalance(player);
    }
}
