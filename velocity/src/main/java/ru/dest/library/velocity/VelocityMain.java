package ru.dest.library.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import ru.dest.library.Library;
import ru.dest.library.command.argument.ArgumentTypes;
import ru.dest.library.command.argument.IArgumentType;
import ru.dest.library.lang.Lang;
import ru.dest.library.lang.impl.ComponentMessage;
import ru.dest.library.plugin.MinecraftPlugin;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static ru.dest.library.utils.Utils.list;

@Plugin(
        id="pluginlibrary",
        name = "PluginLibrary",
        version = "1.0",
        authors = "DestKoder"
)
public class VelocityMain extends MinecraftPlugin<VelocityMain, LibraryConfig> {

    @Inject
    public VelocityMain(@DataDirectory @NotNull Path data, @NotNull Logger logger, ProxyServer server) {
        super(data, logger, server);
    }


    @Override
    public String langFile() {
        return super.langFile();
    }

    @Override
    public Class<LibraryConfig> configClass() {
        return LibraryConfig.class;
    }

    @Override
    public void load() throws Exception {}

    @Override
    public void enable() throws Exception {
        Lang.registerSerializer(config().getMessageType());

        ArgumentTypes.register(Player.class, new IArgumentType<Player>() {
            @Override
            public boolean isValid(String arg) {
                return getServer().getPlayer(arg).isPresent();
            }

            @Override
            public List<String> getCompletions(String arg) {
                return list(getServer().getAllPlayers(), Player::getUsername);
            }

            @Override
            public Player get(String val) {
                return getServer().getPlayer(val).get();
            }
        });

        new Library(logger(), new VelocityMethods(this), getDataFolder());

        Library.get().setConsoleOnlyMessage(new ComponentMessage(config().getMessageConsoleonly()));
        Library.get().setPlayerOnlyMessage(new ComponentMessage(config().getMessagePlayeronly()));
        Library.get().setNoPermissionMessage(new ComponentMessage(config().getMessageNopermission()));
        Library.get().setInternalErrorMessage(new ComponentMessage(config().getMessageInternalError()));
        Library.get().setPlayerNotFoundMessage(new ComponentMessage(config().getMessagePlayerNotFound()));


    }

    @Override
    public void disable() throws Exception {}
}
