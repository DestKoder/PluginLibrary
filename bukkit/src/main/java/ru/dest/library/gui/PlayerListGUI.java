package ru.dest.library.gui;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.bukkit.LibraryMain;
import ru.dest.library.object.FormatPair;
import ru.dest.library.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerListGUI extends ListingGUI<OfflinePlayer>{

    private static GUITemplate checker(GUITemplate template){
        if(LibraryMain.getInstance().skullItem() == null) throw new IllegalStateException("This feature is disabled");
        return template;
    }

    private static @NotNull List<OfflinePlayer> filter(@NotNull List<OfflinePlayer> source, String name){
        List<OfflinePlayer> players = new ArrayList<>();
        source.forEach(p -> {
            if (Objects.requireNonNull(p.getName()).startsWith(name)) players.add(p);
        });
        return players;
    }

    public PlayerListGUI(@NotNull GUITemplate template, Player player, @NotNull List<OfflinePlayer> items, FormatPair... format) {
        super(checker(template), player, items, format);
    }

    public PlayerListGUI(@NotNull GUITemplate template, Player player, @NotNull List<OfflinePlayer> items, String name, FormatPair... format) {
        super(checker(template), player, filter(items, name), format);
    }

    @Override
    protected ItemStack makeItem(OfflinePlayer item) {
        ItemStack head = LibraryMain.getInstance().skullItem();
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(item);
        meta.setDisplayName(ColorUtils.parse("&7" + item.getName()));

        head.setItemMeta(meta);

        return head;
    }
}
