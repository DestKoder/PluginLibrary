package ru.dest.library.economy;

import org.bukkit.OfflinePlayer;

public interface IEconomy {

    EconomyResult giveMoney(OfflinePlayer player, double amount);
    EconomyResult takeMoney(OfflinePlayer player, double amount);

    double getBalance(OfflinePlayer player);
}
