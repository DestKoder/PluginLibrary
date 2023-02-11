package ru.dest.library.utils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.exception.InvalidMaterialException;

import java.util.List;
import java.util.Map;

public class ItemUtils {

    public static final ItemStack EMPTY = new ItemStack(Material.AIR);

    @Contract("_ -> new")
    @NotNull
    public static ItemStack createByMaterial(@NotNull String material){
        material = material.toUpperCase();
        if(!material.contains(":")) {
            Material mat = Material.getMaterial(material);

            if(mat == null) {
                throw new InvalidMaterialException("Material with name " + material + " doesn't exists");
            }
            return new ItemStack(mat);
        }
        String[] data = material.split(":");

        if(data.length != 2) {
            throw new InvalidMaterialException(material);
        }

        Material mat = Material.getMaterial(data[0]);

        if(mat == null) {
            throw new InvalidMaterialException("Material with name " + data[0] + " doesn't exists");
        }

        return new ItemStack(mat, Parser.parseInt(data[1]));
    }

    /**
     * Create {@link ItemStack} with given info
     * @param material - MaterialName of the item
     * @param name - Custom name of the item
     * @param lore - Custom lore of the item
     * @return new {@link ItemStack} with given info;
     */
    @NotNull
    public static ItemStack createItem(@NotNull String material, @Nullable String name, @Nullable List<String> lore){
        ItemStack item = createByMaterial(material);
        ItemMeta meta = item.getItemMeta();

        if(meta == null) return item;

        if(name != null) meta.setDisplayName(ChatUtils.parse(name));
        if(lore != null && !lore.isEmpty()) meta.setLore(ChatUtils.parse(lore));

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Create {@link ItemStack} with given info
     * @param material - {@link Material} of the item
     * @param name - Custom name of the item
     * @param lore - Custom lore of the item
     * @return new {@link ItemStack} with given info;
     */
    @NotNull
    public static ItemStack createItem(@NotNull Material material, @Nullable String name, @Nullable List<String> lore){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if(meta == null) return item;

        if(name != null) meta.setDisplayName(ChatUtils.parse(name));
        if(lore != null && !lore.isEmpty()) meta.setLore(ChatUtils.parse(lore));

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Create {@link ItemStack} with given info
     * @param material - MaterialName of the item
     * @param name - Custom name of the item
     * @param lore - Custom lore of the item
     * @return new {@link ItemStack} with given info;
     */
    @NotNull
    public static ItemStack createItem(@NotNull String material, int id, @Nullable String name, @Nullable List<String> lore){
        ItemStack item = createByMaterial(material);
        ItemMeta meta = item.getItemMeta();

        if(meta == null) return item;

        if(name != null) meta.setDisplayName(ChatUtils.parse(name));
        if(lore != null && !lore.isEmpty()) meta.setLore(ChatUtils.parse(lore));
        meta.setCustomModelData(id);

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Create {@link ItemStack} with given info
     * @param material - {@link Material} of the item
     * @param name - Custom name of the item
     * @param lore - Custom lore of the item
     * @return new {@link ItemStack} with given info;
     */
    @NotNull
    public static ItemStack createItem(@NotNull Material material, int id, @Nullable String name, @Nullable List<String> lore){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if(meta == null) return item;

        if(name != null) meta.setDisplayName(ChatUtils.parse(name));
        if(lore != null && !lore.isEmpty()) meta.setLore(ChatUtils.parse(lore));
        meta.setCustomModelData(id);

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Apply enchantments to item
     * @param item - item to apply
     * @param enchantments Section or map of enchantments and it's levels
     * @return given item with applied enchantments
     */
    @NotNull
    public static ItemStack applyEnchantments(@NotNull ItemStack item, ConfigurationSection enchantments){
        for(String s : enchantments.getKeys(false)){
            Enchantment ench = Enchantment.getByName(s);
            if(ench == null) continue;

            item.addUnsafeEnchantment(ench, enchantments.getInt(s));
        }

        return item;
    }

    /**
     * Apply enchantments to item
     * @param item - item to apply
     * @param enchantments Section or map of enchantments and it's levels
     * @return given item with applied enchantments
     */
    public static ItemStack applyEnchantments(@NotNull ItemStack item, @NotNull Map<String, Integer> enchantments){
        enchantments.forEach((name, level) -> {
            Enchantment ench = Enchantment.getByName(name);
            if(ench != null) item.addUnsafeEnchantment(ench, level);
        });

        return item;

    }

//    public static boolean enchantItem(@NotNull ItemStack item, @NotNull CustomEnchantment enchantment, int level, boolean unsafe){
//        ItemMeta meta = item.getItemMeta();
//
//        if(meta == null) return false;
//
//        if(meta.addEnchant(enchantment, level, unsafe)){
//            List<String> lore = meta.getLore();
//
//            lore.add(ChatUtils.parse(enchantment.getName() + getArabicAlias(level)));
//
//            meta.setLore(lore);
//            item.setItemMeta(meta);
//            return true;
//        }else return false;
//    }

    @NotNull
    private static String getArabicAlias(int i) {
        switch (i){
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
            case 7:
                return "VII";
            case 8:
                return "VIII";
            case 9:
                return "IX";
            case 10:
                return "X";
            default:
                return i+"";
        }
    }
}
