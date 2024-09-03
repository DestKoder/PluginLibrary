package ru.dest.library.module.economy;

import org.bukkit.OfflinePlayer;
import ru.dest.library.module.Module;

public interface EconomyModule extends Module {

    enum Result {
        SUCCESS, ERROR_INTERNAL, ERROR_BALANCE;

        boolean isSuccess(){ return this == SUCCESS; }
    }

    boolean give(OfflinePlayer player, int amount);
    boolean take(OfflinePlayer player, int amount);
    boolean has(OfflinePlayer player, int amount);
    int getBalance(OfflinePlayer player);

}
