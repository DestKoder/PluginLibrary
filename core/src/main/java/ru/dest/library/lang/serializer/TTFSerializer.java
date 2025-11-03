package ru.dest.library.lang.serializer;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.lang.SerializerType;

public final class TTFSerializer implements ComponentSerializer<Component, Component, String> {
    //"&7&oHello, this is a &bsample json&7.||ttp:&bI'm a tooltip for the first cluster.|| There's no tag, so I've started a new cluster.||cmd:/ping||ttp:&6&o&
    private static TTFSerializer instance;

    private TTFSerializer() {
        instance = this;
    }

    public static TTFSerializer get(){
        return instance == null ? new TTFSerializer() : instance;
    }

    @Override
    public @NotNull Component deserialize(@NotNull String input) {
        Component result = null;

        if (input.isEmpty()) return Component.text(input);

        String[] rawData = input.split("\\|\\|");

        Component comp = null;

        for(String s : rawData){
            if(s.startsWith("ttp:") && comp != null){
                comp = comp.hoverEvent(SerializerType.LEGACY.get().deserialize(s.substring("ttp:".length())).asHoverEvent());
            }else if (s.startsWith("cmd:") && comp != null){
                String cmd = s.substring("cmd:".length());
                comp = comp.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, cmd.startsWith("/")?cmd:"/"+cmd));
            }else if (s.startsWith("sgt:") && comp != null){
                comp = comp.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, s.substring("sgt:".length())));
            }else if (s.startsWith("url:") && comp != null){
                comp = comp.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, s.substring("url:".length())));
            }else if (s.startsWith("copy:") && comp != null) {
                comp = comp.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, s.substring("copy:".length())));
            }else{
                //Если текущий компонент = null, сохраняем в него текущую строчку
                if(comp == null){
                    comp = SerializerType.LEGACY.get().deserialize(s);
                }else {
                    if(result == null) result = Component.text("").append(comp);
                    result = result.appendNewline().append(comp);
                    comp = SerializerType.LEGACY.get().deserialize(s);
                }
            }
        }
        return result == null ? comp == null ? Component.text("") : Component.text("").append(comp) : result;
    }

    @Override
    public @NotNull String serialize(@NotNull Component component) {
        return "";
    }
}
