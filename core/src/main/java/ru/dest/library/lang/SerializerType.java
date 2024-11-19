package ru.dest.library.lang;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import ru.dest.library.dependency.RuntimeDependency;

import java.util.Optional;
import java.util.function.Supplier;

public enum SerializerType {

    LEGACY(() -> {
        ComponentSerializer<? extends Component, ? extends Component, String> serializer;
        try {
            Optional<ClassLoader> opt = RuntimeDependency.loadIfAbsent("net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer", "https://dk-develop.ru/lib/kyori-serializer/legacy");
            if(opt.isPresent()){
                serializer = (ComponentSerializer<? extends Component, ? extends Component, String>) opt.get().loadClass("net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer").getMethod("legacyAmpersand").invoke(null);
            }else {
                serializer = (ComponentSerializer<? extends Component, ? extends Component, String>) Class.forName("net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer").getMethod("legacyAmpersand").invoke(null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(serializer == null) throw new RuntimeException("Error initalizing serailzier");

        return serializer;
    }),
    MINI_MESSAGE(() -> {
        ComponentSerializer<? extends Component, ? extends Component, String> serializer;
        try {
            Optional<ClassLoader> opt = RuntimeDependency.loadIfAbsent("net.kyori.adventure.text.minimessage.MiniMessage", "https://dk-develop.ru/lib/kyori-serializer/minimsg");
            if(opt.isPresent()) {
                serializer = (ComponentSerializer<? extends Component, ? extends Component, String>) opt.get().loadClass("net.kyori.adventure.text.minimessage.MiniMessage").getMethod("miniMessage").invoke(null);
            }else{
                serializer = (ComponentSerializer<? extends Component, ? extends Component, String>) Class.forName("net.kyori.adventure.text.minimessage.MiniMessage").getMethod("miniMessage").invoke(null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(serializer == null) throw new RuntimeException("Error initalizing serailzier");

        return serializer;
    });
    ;
    private final Supplier<ComponentSerializer<? extends Component, ? extends Component, String>> serializer;

    SerializerType(Supplier<ComponentSerializer<? extends Component, ? extends Component, String>> serializer) {
        this.serializer = serializer;
    }

    public ComponentSerializer<? extends Component, ? extends Component, String> get(){return serializer.get();}
}
