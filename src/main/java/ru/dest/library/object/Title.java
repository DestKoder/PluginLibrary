package ru.dest.library.object;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.utils.ChatUtils;

import java.util.List;

public class Title {

    private final String title;
    private final String subtitle;

    public Title(@NotNull String title,@Nullable String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public Title formatTitle(List<Pair<String, String>> format){
        return new Title(ChatUtils.formatMessage(title, format), subtitle);
    }

    public Title formatSubTitle(List<Pair<String, String>> format){
        if(subtitle == null) return this;
        return new Title(title,ChatUtils.formatMessage(subtitle, format));
    }

    public Title formatAll(List<Pair<String,String>> format){
        if(subtitle == null) return formatTitle(format);

        return new Title(ChatUtils.formatMessage(title, format), ChatUtils.formatMessage(subtitle, format));
    }

    public void show(Player player){
        player.sendTitle(title, subtitle == null ? "" : subtitle, 20, 20, 20);
    }

    public void show(Player player, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(title, subtitle == null ? "" : subtitle, fadeIn, stay, fadeOut);
    }
}
