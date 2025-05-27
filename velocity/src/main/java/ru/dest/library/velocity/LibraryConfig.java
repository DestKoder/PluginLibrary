package ru.dest.library.velocity;

import lombok.Getter;
import ru.dest.library.config.BaseConfig;
import ru.dest.library.config.ann.ConfigPath;
import ru.dest.library.lang.SerializerType;

import java.io.File;
@Getter
public class LibraryConfig extends BaseConfig {


    public LibraryConfig(File file) {
        super(file);
    }

    private final String messagePlayeronly = "&cThis command available only for players";
    private final String messageConsoleonly = "&cThis command available only for console";
    private final String messageNopermission = "&cYou don't have enough permissions to use this";
    @ConfigPath("message.internal-error")
    private final String messageInternalError = "&cInternal error occupied. Please contact an administrator. Admin, see console for more details";
    @ConfigPath("message.player-not-found")
    private final String messagePlayerNotFound = "&cPlayer {player} isn't online";

    @ConfigPath("message-type")
    private final SerializerType messageType = SerializerType.LEGACY;
}
