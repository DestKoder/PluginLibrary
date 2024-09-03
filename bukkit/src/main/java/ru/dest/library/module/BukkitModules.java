package ru.dest.library.module;

import ru.dest.library.module.economy.EconomyModule;
import ru.dest.library.module.economy.PlayerPointsEconomy;
import ru.dest.library.module.economy.VaultEconomy;
import ru.dest.library.module.permission.PermissionModule;

public final class BukkitModules {

    public static final Class<EconomyModule> DEFAULT_ECONOMY = EconomyModule.class;
    public static final Class<VaultEconomy> VAULT_ECONOMY = VaultEconomy.class;
    public static final Class<PlayerPointsEconomy> PLAYER_POINTS = PlayerPointsEconomy.class;

    public static final Class<PermissionModule> PERMISSION = PermissionModule.class;
}
