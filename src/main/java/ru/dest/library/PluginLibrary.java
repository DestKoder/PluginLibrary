package ru.dest.library;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.dest.library.economy.Economies;
import ru.dest.library.exception.IntegrationInitException;
import ru.dest.library.gui.GUI;
import ru.dest.library.integration.vault.VaultEconomy;
import ru.dest.library.task.TaskManager;
import ru.dest.library.utils.ConsoleLogger;

public final class PluginLibrary extends JavaPlugin implements Listener {

    private static PluginLibrary instance;

    private TaskManager tM;
    private Economies economies;

    @Override
    public void onEnable() {
        instance = this;
        ConsoleLogger logger = new ConsoleLogger(getName(), false);
        tM = TaskManager.get();
        getServer().getPluginManager().registerEvents(this,this);

        logger.info("Loading integrations...");
        economies = new Economies();

        try {
            VaultEconomy econ = new VaultEconomy(getServer());
            economies.r(VaultEconomy.ID, econ);
            logger.info("Vault economy loaded");
        } catch (IntegrationInitException e) {
            getLogger().info("Vault economy load failed: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        tM.cancelAll();
    }

    @EventHandler
    public void ic(InventoryClickEvent e){
        if(e.getClickedInventory() == null) return;
        if(e.getClickedInventory().getHolder() == null) return;

        if(!(e.getClickedInventory().getHolder() instanceof GUI))return;
        ((GUI)e.getClickedInventory().getHolder()).onClick(e);
    }

    @EventHandler
    public void id(InventoryDragEvent e){
        if(e.getInventory().getHolder() == null) return;
        if(!(e.getInventory().getHolder() instanceof GUI))return;

        ((GUI)e.getInventory().getHolder()).onDrag(e);
    }

    @EventHandler
    public void icl(InventoryCloseEvent e){
        if(e.getInventory().getHolder() == null) return;
        if(!(e.getInventory().getHolder() instanceof GUI)) return;

        ((GUI)e.getInventory().getHolder()).onClose(e);
    }

    public Economies getEconomies(){return economies;}

    public PluginLibrary get() { return instance;}
}
