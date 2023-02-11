package ru.dest.library.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.exception.MissingConfigurationException;

public class ConfigUtils {
    /**
     * Save location to {@link ConfigurationSection}
     * @param loc - {@link Location} to save;
     * @param section - {@link ConfigurationSection} in which location will be saved
     * @param saveYawAndPitch is needed to save yaw and pitch?
     */
    public static void saveLocation(@NotNull Location loc,@NotNull ConfigurationSection section, boolean saveYawAndPitch) {
        if(loc.getWorld() == null) return;

        section.set("world", loc.getWorld().getName());
        section.set("posX", loc.getBlockX());
        section.set("posY", loc.getBlockY());
        section.set("posZ", loc.getBlockZ());

        if(saveYawAndPitch){
            section.set("yaw", loc.getYaw());
            section.set("pitch", loc.getPitch());
        }
    }

    /**
     * Load location from config
     * @param section section to load Location from
     * @return {@link Location} loaded from section;
     * @throws MissingConfigurationException if one of required arguments is missing
     */
    @NotNull
    public static Location loadLocation(@NotNull ConfigurationSection section){

        if(!section.isSet("world") || !section.isSet("x") || !section.isSet("y") || !section.isSet("z")) throw new MissingConfigurationException("Invalid location config. One of values (world, x, y, z) is missing");

        World world = Bukkit.getWorld(section.getString("world"));

        if(world == null){
            throw new NullPointerException("Location world doesn't exists!");
        }

        Location location = new Location(world, section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));

        if(section.isSet("yaw")) location.setYaw((float) section.getDouble("yaw"));
        if(section.isSet("pitch")) location.setPitch((float) section.getDouble("pitch"));

        return location;
    }

    /* Working with items */

    /**
     * Load item from config
     * @param section - {@link ConfigurationSection} with item data
     * @return {@link ItemStack} loaded from given section
     */
    public static ItemStack getItem(@NotNull ConfigurationSection section) {
        if(!section.isSet("material") || !section.isString("material")) throw new MissingConfigurationException("Invalid configuration: Missing key 'material'");

        ItemStack item = ItemUtils.createByMaterial(section.getString("material"));

        ItemMeta meta = item.getItemMeta();

        if(meta == null) return item;

        if(section.isSet("name") && section.isString("name")) meta.setDisplayName(ChatUtils.parse(section.getString("name")));
        if(section.isSet("lore") && section.isList("lore")) meta.setLore(ChatUtils.parse(section.getStringList("lore")));
        if(section.isInt("model") && section.isInt("model")) meta.setCustomModelData(section.getInt("model"));


        item.setItemMeta(meta);

        if(section.isSet("amount")) item.setAmount(section.getInt("amount"));
        if(section.isSet("enchantments") && section.isConfigurationSection("enchantments")) item = ItemUtils.applyEnchantments(item, section.getConfigurationSection("enchantments"));

        return item;
    }
}
