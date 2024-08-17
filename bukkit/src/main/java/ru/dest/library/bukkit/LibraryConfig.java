package ru.dest.library.bukkit;

import lombok.AccessLevel;
import lombok.Getter;
import ru.dest.library.config.BaseConfig;
import ru.dest.library.config.ann.ConfigPath;
import ru.dest.library.lang.SerializerType;

import java.io.File;
@Getter
public class LibraryConfig extends BaseConfig {

    private String materialHead = "PLAYER_SKULL";
    private String messagePlayeronly = "&cThis command available only for players";
    private String messageConsoleonly = "&cThis command available only for console";
    private String messageNopermission = "&cYou don't have enough permissions to use this";
    @ConfigPath("message.internal-error")
    private String messageInternalError = "&cInternal error occupied. Please contact an administrator. Admin, see console for more details";
    @ConfigPath("message.player-not-found")
    private String messagePlayerNotFound = "&cPlayer {player} isn't online";


    @ConfigPath("message-type")
    private SerializerType messageType = SerializerType.LEGACY;

    public LibraryConfig(File file) {
        super(file);
    }
}
