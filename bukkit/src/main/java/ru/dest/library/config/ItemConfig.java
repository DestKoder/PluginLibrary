package ru.dest.library.config;

import lombok.AllArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.bukkit.LibraryMain;
import ru.dest.library.exception.InvalidItemConfigurationException;
import ru.dest.library.object.FormatPair;
import ru.dest.library.utils.ColorUtils;
import ru.dest.library.utils.FormatUtils;
import ru.dest.library.utils.ItemUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.dest.library.utils.Utils.list;

@AllArgsConstructor
public class ItemConfig {

    private final String material;
    private final String name;
    private final List<String> lore;
    private final Map<String, Integer> enchantments;
    private final String texture;
    private final String color;
    private final int model;

    public ItemStack makeItem(Player player, FormatPair... additionalFormat){
        ItemStack item = ItemUtils.getByMaterial(material);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;

        if(name != null) {
            if(LibraryMain.getInstance().isPapiEnable()) meta.setDisplayName(ColorUtils.parse(PlaceholderAPI.setPlaceholders(player, FormatUtils.format(name, additionalFormat))));
            else meta.setDisplayName(ColorUtils.parse(FormatUtils.format(name, additionalFormat)));
        }

        if(lore != null){
            if(LibraryMain.getInstance().isPapiEnable()){
                meta.setLore(ColorUtils.parse(list(lore, line -> PlaceholderAPI.setPlaceholders(player, FormatUtils.format(line, additionalFormat)))));
            }else meta.setLore(list(ColorUtils.parse(lore), l -> FormatUtils.format(l, additionalFormat)));
        }

        if(model != -1){
            meta.setCustomModelData(model);
        }

        if(texture != null && meta instanceof SkullMeta){
            meta = ItemUtils.setHeadTexture((SkullMeta) meta, texture);
        }

        if(color != null){
            meta = ItemUtils.setColor(meta, color);
        }

        if(enchantments != null){
            for(String id : enchantments.keySet()){
                meta.addEnchant(Enchantment.getByKey(NamespacedKey.fromString(id)), enchantments.get(id), true);
            }
        }

        item.setItemMeta(meta);

        return item;
    }

    @Contract("_ -> new")
    public static @NotNull ItemConfig load(@NotNull ConfigurationSection section){
        if(!section.isSet("material")) throw new InvalidItemConfigurationException("material");
        String material = (String)section.get("material");
        String name = section.isSet("name") ? section.getString("name") : null;
        List<String> lore = section.isSet("lore") ? section.getStringList("lore") : null;
        String texture = section.isSet("texture") ? section.getString("texture") : null;
        String color = section.isSet("color") ? section.getString("color") : null;
        int model = section.isSet("model") ? section.getInt("model") : -1;

        Map<String, Integer> enchantments = null;

        if(section.isSet("enchantments")){
            enchantments = new HashMap<>();
            for(String s : section.getConfigurationSection("enchantments").getKeys(false)){
                enchantments.put(s, section.getInt("enchantments."+s));
            }
        }

        return new ItemConfig(material, name, lore, enchantments, texture, color, model);
    }


}
