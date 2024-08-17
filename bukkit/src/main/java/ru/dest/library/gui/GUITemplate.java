package ru.dest.library.gui;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.config.ItemConfig;
import ru.dest.library.exception.InvalidGUIConfigurationException;

import java.util.*;


public class GUITemplate {

    @Getter
    private final List<GUIItem> items;
    @Getter
    private final int rows;
    @Getter
    private final String title;

    private final Map<String, Integer[]> systemHandlers;

    private GUITemplate(List<GUIItem> items, int rows, String title, Map<String, Integer[]> systemHandlers) {
        this.items = items;
        this.rows = rows;
        this.title = title;
        this.systemHandlers = systemHandlers;
    }

    @Contract("_ -> new")
    public static @NotNull GUITemplate load(@NotNull FileConfiguration cfg) {
        if(!cfg.isSet("rows")) throw new InvalidGUIConfigurationException("Missing required key: rows");
        int rows = cfg.getInt("rows");
        String title = cfg.isSet("title") ? cfg.getString("title") : "Some Plugin GUI";
        Map<String, Integer[]> systemHandlers = new HashMap<>();
        if(cfg.isSet("system")){
            ConfigurationSection system = cfg.getConfigurationSection("system");
            if(system == null) throw new InvalidGUIConfigurationException("system must be a section.");

            for(String s : system.getKeys(false)){
                if(system.isInt(s)) systemHandlers.put(s, new Integer[]{system.getInt(s)});
                else if(system.isList(s)) systemHandlers.put(s, system.getIntegerList(s).toArray(new Integer[0]));
                else throw new InvalidGUIConfigurationException("Value of system handler must be an integer or a list of integer");
            }
        }

        if(!cfg.isSet("items")) throw new InvalidGUIConfigurationException("Missing required key: items");
        ConfigurationSection items = cfg.getConfigurationSection("items");
        if(items == null) throw new InvalidGUIConfigurationException("items must be a section");

        List<GUIItem> guiItems = new ArrayList<>();
        for(String i : items.getKeys(false)){
            ConfigurationSection item = items.getConfigurationSection(i);
            if(item == null) throw new InvalidGUIConfigurationException(i + " in items sections must be a section");
            ItemConfig config = null;
            try{
                config = ItemConfig.load(item);
            }catch (Exception e){
                throw new InvalidGUIConfigurationException("Error loading item " + i + ": " + e.getMessage());
            }
            int[] slots = null;
            if(item.isSet("slot")){
                if(item.isInt("slot")) slots = new int[]{item.getInt("slot")};
                if(item.isString("slot")){
                    String[] data = item.getString("slot").split(":");
                    if(data.length != 2){
                        throw new InvalidGUIConfigurationException("Invalid slot value for item " + i + ": it must be an integer or string in format row:item");
                    }
                    int row = Integer.parseInt(data[0]);
                    int slot = Integer.parseInt(data[1]);
                    if(row == 1) slots = new int[]{slot-1};
                    else slots = new int[]{row*9+slot -1};
                }
            }
            if(item.isSet("slots")){
//                System.out.println(item.get("slots"));
                if(item.isList("slots")){//Arrays.stream(arr2).mapToInt(Integer::intValue).toArray()
                    slots = Arrays.stream(item.getIntegerList("slots").toArray(new Integer[0])).mapToInt(Integer::intValue).toArray();
                }
            }
            if(slots == null){
                throw new InvalidGUIConfigurationException("Missing required key for item " + i + ": slot or slots must be given");
            }

            guiItems.add(new GUIItem(config, slots,
                    item.isSet("right_click_commands") ? item.getStringList("right_click_commands") : new ArrayList<String>(),
                    item.isSet("right_click_commands") ? item.getStringList("left_click_commands") : new ArrayList<String>(),
                    item.isSet("right_click_commands") ? item.getStringList("click_commands") : new ArrayList<String>(),
                    item.isSet("right_click_commands") ? item.getStringList("middle_click_commands") : new ArrayList<String>()
            ));
        }
//        System.out.println(guiItems);

        return new GUITemplate(guiItems, rows, title, systemHandlers);
    }

    public Integer[] getHandler(String s){
        return systemHandlers.get(s);
    }
}
