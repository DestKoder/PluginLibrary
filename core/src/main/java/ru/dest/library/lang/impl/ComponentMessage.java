package ru.dest.library.lang.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;
import ru.dest.library.lang.Lang;
import ru.dest.library.lang.Message;
import ru.dest.library.object.FormatPair;
import ru.dest.library.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

public class ComponentMessage implements Message {

    private String val;
    private ClickEvent clickEvent;
    private HoverEvent<?> hoverEvent;
    private final List<ComponentMessage> additions;

    public ComponentMessage(String val) {
        this.val = val;
        this.additions = new ArrayList<>();
    }

    public Component build(){
        Component base = Lang.serializer().deserialize(val).clickEvent(clickEvent).hoverEvent(hoverEvent);

        for(ComponentMessage msg : additions){
            base = base.append(msg.build());
        }

        return base;
    }

    @Override
    public @NotNull Message setHoverText(@NotNull String text) {
        this.hoverEvent = HoverEvent.showText(Component.text(text));
        return this;
    }

    @Override
    public @NotNull Message setRunCommandOnClick(@NotNull String cmd) {
        this.clickEvent = ClickEvent.runCommand(cmd);
        return this;
    }

    @Override
    public @NotNull Message setSuggestCommandOnClick(@NotNull String cmd) {
        this.clickEvent = ClickEvent.suggestCommand(cmd);
        return this;
    }

    @Override
    public @NotNull Message setOpenUrlOnClick(@NotNull String url) {
        this.clickEvent = ClickEvent.openUrl(url);
        return this;
    }

    @Override
    public @NotNull Message setCopyTextOnClick(@NotNull String text) {
        this.clickEvent = ClickEvent.copyToClipboard(text);
        return this;
    }

    @Override
    public @NotNull String getRaw() {
        return val;
    }

    @Override
    public @NotNull Message add(@NotNull Message message) {
        if(!(message instanceof ComponentMessage)) throw new IllegalArgumentException("Component Message supports adding only ComponentMessages.");
        additions.add(( ComponentMessage)message);
        return this;
    }

    @Override
    public Message format(String key, String value) {
        this.val = FormatUtils.format(val, key, value);
        return this;
    }

    @Override
    public Message format(FormatPair... format) {
        this.val = FormatUtils.format(val, format);
        return this;
    }

    @Override
    public Message format(List<FormatPair> format) {
        this.val = FormatUtils.format(val, format);
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
        return val;
    }

    @Override
    public void kick(Object player) {
        Library.get().getMethods().kick(player, build());
    }
}
