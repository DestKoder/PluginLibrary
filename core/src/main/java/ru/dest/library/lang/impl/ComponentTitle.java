package ru.dest.library.lang.impl;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.Library;
import ru.dest.library.lang.Title;
import ru.dest.library.object.FormatPair;
import ru.dest.library.utils.FormatUtils;

import java.util.List;

public class ComponentTitle implements Title<Component> {

    @NotNull
    private String title;
    @Nullable
    private String subTitle;

    public ComponentTitle(@NotNull String title, @Nullable String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    @Override
    public Component getTitle() {
        return Component.text(title);
    }

    @Override
    public Component getSubtitle() {
        return subTitle == null ? null : Component.text(subTitle);
    }
;
    @Override
    public Title<Component> format(String key, String value) {
        this.title = FormatUtils.format(title, key, value);
        if(subTitle != null) subTitle = FormatUtils.format(subTitle, key, value);
        return this;
    }

    @Override
    public Title<Component> format(FormatPair... format) {
        this.title = FormatUtils.format(title, format);
        if(subTitle != null) subTitle = FormatUtils.format(subTitle,format);
        return this;
    }

    @Override
    public Title<Component> format(List<FormatPair> format) {
        this.title = FormatUtils.format(title, format);
        if(subTitle != null) subTitle = FormatUtils.format(subTitle,format);
        return this;
    }

    @Override
    public void send(Object receiver) {
        Library.get().getMethods().send(receiver, this);
    }

    @Override
    public void broadcast() {
        Library.get().getMethods().broadcast(this);
    }

    @Override
    public void broadcast(String permission) {
        Library.get().getMethods().broadcast(this, permission);
    }

    @Override
    public String raw() {
        return title + ";;" + subTitle;
    }
}
