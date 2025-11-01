package ru.dest.library.bukkit;

import lombok.Getter;
import ru.dest.library.config.BaseConfig;
import ru.dest.library.config.ann.ConfigPath;
import ru.dest.library.lang.SerializerType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
public class LibraryConfig extends BaseConfig {

    private final String materialHead = "PLAYER_SKULL";

    private final Map<String, String> placeholders = new HashMap<>();

    private final String messagePlayeronly = "&cThis command available only for players";
    private final String messageConsoleonly = "&cThis command available only for console";
    private final String messageNopermission = "&cYou don't have enough permissions to use this";
    @ConfigPath("message.internal-error")
    private final String messageInternalError = "&cInternal error occupied. Please contact an administrator. Admin, see console for more details";
    @ConfigPath("message.player-not-found")
    private final String messagePlayerNotFound = "&cPlayer {player} isn't online";

    @ConfigPath("message.no-such-plugin")
    private final String messageNoSuchPlugin = "&aPlugin {plugin} not found!";
    @ConfigPath("message.not-library-plugin")
    private final String messageNotLibraryPlugin = "&aPlugin {plugin} is not a library plugin!";


    @ConfigPath("message-type")
    private final SerializerType messageType = SerializerType.LEGACY;

    public LibraryConfig(File file) {
        super(file);
    }
}
