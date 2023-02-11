package ru.dest.library.gui;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.dest.library.utils.ConfigUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static ru.dest.library.utils.ChatUtils.parse;

/**
 * This class represent a template of gui, loading from config
 *
 * @since 1.0
 * @author DestKoder
 */
public class GUITemplate {

    private static final String PLAYER_CMD = "[player]";
    private static final String CONSOLE_CMD = "[console]";
    private static final String MESSAGE_CMD = "[message]";

    private final int slots;
    private String title;
    private final Map<List<Integer>, ItemStack> items;
    private final Map<List<Integer>, Consumer<InventoryClickEvent>> handlers;
    private final Map<String, Integer> systemHandlers;

    protected GUITemplate(int slots, String title, Map<List<Integer>, ItemStack> items, Map<List<Integer>, Consumer<InventoryClickEvent>> handlers, Map<String, Integer> systemHandlers) {
        this.slots = slots;
        this.title = title;
        this.items = items;
        this.handlers = handlers;
        this.systemHandlers = systemHandlers;
    }

    public Map<List<Integer>, ItemStack> getItems() {
        return items;
    }

    public static GUITemplate fromConfig(FileConfiguration config){
        int slots = config.getInt("rows")*9;
        String title = config.getString("title");

        Map<List<Integer>, ItemStack> items = new HashMap<>();
        Map<List<Integer>, Consumer<InventoryClickEvent>> handlers = new HashMap<>();

        ConfigurationSection itemsSection = config.getConfigurationSection("items");

        for(String s : itemsSection.getKeys(false)){
            items.put(itemsSection.getIntegerList(s+".slots"), ConfigUtils.getItem(itemsSection.getConfigurationSection(s)));
            Set<String> keys = itemsSection.getConfigurationSection(s).getKeys(false);

            if(keys.contains("onClick")) {
                List<String> actions = itemsSection.getStringList(s+".onClick");

                handlers.put(itemsSection.getIntegerList(s+".slots"), event -> {
                    actions.forEach(action -> {
                        Player player = (Player) event.getWhoClicked();

                        if(action.startsWith(PLAYER_CMD)){
                            Bukkit.dispatchCommand(player,action.substring(PLAYER_CMD.length()).trim());
                            return;
                        }

                        if(action.startsWith(CONSOLE_CMD)){
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), action.substring(CONSOLE_CMD.length()).replace("%player%", player.getName()).trim());
                            return;
                        }

                        if(action.startsWith(MESSAGE_CMD)){
                            player.sendMessage(parse(action.substring(MESSAGE_CMD.length()).trim()));
                        }
                    });
                });
            }
        }

        ConfigurationSection system = config.getConfigurationSection("system");

        Map<String, Integer> systemHandlers = new HashMap<>();

        for(String s : system.getKeys(false)){
            systemHandlers.put(s, system.getInt(s));
        }

        return new GUITemplate(slots, title, items, handlers, systemHandlers);
    }

    public GUITemplate setTitle(String newTitle){
        this.title = newTitle;

        return this;
    }

    public int getSlots() {
        return slots;
    }

    public String getTitle() {
        return title;
    }

    public Map<List<Integer>, Consumer<InventoryClickEvent>> getHandlers() {
        return handlers;
    }

    public Map<String, Integer> getSystemHandlers() {
        return systemHandlers;
    }
}
