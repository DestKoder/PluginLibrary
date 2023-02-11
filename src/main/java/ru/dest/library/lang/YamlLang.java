package ru.dest.library.lang;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.object.Message;
import ru.dest.library.object.Title;
import ru.dest.library.utils.ChatUtils;

public class YamlLang implements Lang{

    private final FileConfiguration cfg;

    public YamlLang(FileConfiguration cfg) {
        this.cfg = cfg;
    }

    @Override
    public String getRawString(String key) {
        return cfg.getString(key);
    }

    @Override
    public @Nullable Message getMessage(String key) {
        String msg = cfg.getString(key);
        return msg == null ? null : new Message(msg);
    }

    @Override
    public Title getTitle(String key) {
        String title = cfg.getString(key + ".title"), subtitle = cfg.getString(key+".subtitle");
        if(title == null) return null;

        return new Title(title, subtitle);
    }

    @Override
    public Message getMessage(String key, Player placeholder) {
        String msg = cfg.getString(key);
        if(msg == null) return null;
        msg = ChatUtils.applyPlaceholders(msg, placeholder);
        return new Message(msg);
    }

    @Override
    public Title getTitle(String key, Player placeholder) {
        String title = cfg.getString(key + ".title"), subtitle = cfg.getString(key+".subtitle");
        if(title == null) return null;
        if(subtitle != null) subtitle = ChatUtils.applyPlaceholders(subtitle, placeholder);
        title = ChatUtils.applyPlaceholders(title, placeholder);

        return new Title(title, subtitle);
    }
}
