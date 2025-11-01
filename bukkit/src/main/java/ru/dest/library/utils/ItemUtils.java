package ru.dest.library.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.experimental.UtilityClass;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import ru.dest.library.exception.InvalidMaterialException;
import ru.dest.library.item.ItemProviders;
import java.lang.reflect.Field;
import java.util.*;


@UtilityClass
public class ItemUtils {

    public ItemStack getByMaterial(String material){
        if(material.startsWith("minecraft:")) material = material.substring("minecraft:".length());
//        material = material.toUpperCase();
        if(!material.contains(":")){
            Material mat = Material.getMaterial(material.toUpperCase());

            if(mat == null) throw new InvalidMaterialException(material.toUpperCase());

            return new ItemStack(mat);
        }
        String[] data = material.split(":");

//        if(mat == null) throw new InvalidMaterialException(data[0]);

        if(data.length == 1){
            return new ItemStack(Objects.requireNonNull(Material.getMaterial(data[0].toUpperCase())));
        }

        if(data.length == 2 && data[1].matches(Patterns.INTEGER)) return new ItemStack(Objects.requireNonNull(Material.getMaterial(data[0].toUpperCase())), Integer.parseInt(data[1]));

        if(data[0].equalsIgnoreCase("minecraft")) return new ItemStack(Objects.requireNonNull(Material.getMaterial(data[1])));

        ItemStack item = ItemProviders.find(new NamespacedKey(data[0], data[1]));

        if(item == null) throw new IllegalArgumentException("Unknown item "+data[0] + ":" + data[1]);

        return item;
    }

    public SkullMeta setHeadTexture(SkullMeta meta, String texture){
        UUID uuid = UUID.randomUUID();
        GameProfile profile = new GameProfile(uuid, uuid.toString());

        profile.getProperties().put("textures", new Property("textures", texture));

        try{
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
            return meta;
        }catch (Exception e){
            return meta;
        }
    }

    public ItemMeta setColor(ItemMeta meta, String color){
        if(! (meta instanceof LeatherArmorMeta) && !(meta instanceof PotionMeta)) return meta;
        Color c = ColorUtils.getColorFromHexString(color);
        if(meta instanceof LeatherArmorMeta){
            ((LeatherArmorMeta)meta).setColor(c);
        }

        if(meta instanceof PotionMeta){
            ((PotionMeta)meta).setColor(c);
        }

        return meta;
    }

    public ItemStack fromJson(JsonObject object){
        String itemId = object.get("item").getAsString();
        ItemStack item;
        if(itemId.startsWith("minecraft:")){
            item = new ItemStack(Objects.requireNonNull(Material.getMaterial(itemId.split(":")[1].toUpperCase())));
        }else{
            item = ItemProviders.find(Objects.requireNonNull(NamespacedKey.fromString(itemId)));
        }

        if(item == null) throw new IllegalArgumentException("Invalid itemId in object");

        if(object.has("amount")) item.setAmount(object.get("amount").getAsInt());

        ItemMeta meta = item.getItemMeta();
        if(object.has("meta")){
            JsonObject metaObj = object.get("meta").getAsJsonObject();

            meta.setDisplayName(metaObj.get("displayName").getAsString());
            List<String> lore = new ArrayList<>();
            for(JsonElement e : metaObj.getAsJsonArray("lore")){
                lore.add(e.getAsString());
            }
            meta.setLore(lore);
            meta.setCustomModelData(metaObj.get("model").getAsInt());

            if(metaObj.has("enchantments")) {
                JsonObject enchantments = metaObj.getAsJsonObject("enchantments");

                for(Map.Entry<String, JsonElement> enchantment : enchantments.entrySet()){
                    meta.addEnchant(Enchantment.getByKey(NamespacedKey.fromString(enchantment.getKey())), enchantment.getValue().getAsInt(), true);
                }
            }

            if(metaObj.has("flags")){
                for(JsonElement element : metaObj.getAsJsonArray("flags")){
                    meta.addItemFlags(ItemFlag.valueOf(element.getAsString()));
                }
            }
        }
        item.setItemMeta(meta);

        return item;
    }


//    public JsonObject toJson(ItemStack item){
//        JsonObject object = new JsonObject();
////        Item cItem = ItemsRegistry.current().byItemStack(item);
////        if(cItem != null) object.addProperty("item", cItem.getId().toString());
////        else object.addProperty("item", "minecraft:"+item.getType().name().toLowerCase());
//        object.addProperty("amount", item.getAmount());
//        ItemMeta meta = item.getItemMeta();
//        if(meta != null){
//            JsonObject metaObj = new JsonObject();
//            metaObj.addProperty("displayName", meta.getDisplayName());
//            JsonArray lore = new JsonArray();
//            meta.getLore().forEach( s ->lore.add(new JsonPrimitive(s)));
//            metaObj.add("lore", lore);
//
//            metaObj.addProperty("model", meta.getCustomModelData());
//
//            Map<Enchantment, Integer> enchant = meta.getEnchants();
//            if(!enchant.isEmpty()){
//                JsonObject enchants = new JsonObject();
//                enchant.forEach((enchantment, level) -> {
//                    enchants.addProperty(enchantment.getKey().toString(), level);
//                });
//                metaObj.add("enchantments", enchants);
//            }
//            Set<ItemFlag> flags = meta.getItemFlags();
//            if(!flags.isEmpty()){
//                JsonArray flagsArray =new JsonArray();
//                for(ItemFlag flag : flags){
//                    flagsArray.add(flag.name());
//                }
//                metaObj.add("flags",flagsArray);
//            }
//
//            object.add("meta", metaObj);
//        }
//
////        if(cItem != null) return cItem.toJson(object);
//        return object;
//    }

}
