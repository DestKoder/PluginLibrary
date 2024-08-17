package ru.dest.library.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.dest.library.utils.Utils.list;

@UtilityClass
public final class ColorUtils {

    private static final Pattern HEX_PATTERN = Pattern.compile("(&#[0-9A-Fa-f]{6})");

    @Contract("_ -> new")
    public @NotNull String parse(String s){
        Matcher matcher = HEX_PATTERN.matcher(s);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            String hex = matcher.group(1).substring(1);
            matcher.appendReplacement(sb, "" + getColorFromHexString(hex));
        }

        matcher.appendTail(sb);
        String hexColored = sb.toString();

        return ChatColor.translateAlternateColorCodes('&', hexColored);
    }

    public @NotNull List<String> parse(List<String> l){
        return list(l, ColorUtils::parse);
    }

    @Contract("_ -> new")
    public @NotNull Color getColorFromHexString(@NotNull String hex){
        return Color.fromRGB(
                Integer.valueOf(hex.substring(1,3), 16),
                Integer.valueOf(hex.substring(3, 5), 16),
                Integer.valueOf(hex.substring(5, 7), 16)
        );
    }

}
