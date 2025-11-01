package ru.dest.library;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import ru.dest.library.lang.Message;
import ru.dest.library.logging.ILogger;
import ru.dest.library.plugin.PlatformMethods;

import java.io.File;

/**
 * Core library class for access some library features.
 */
@Getter
public class Library {
    public static GsonBuilder builder = new GsonBuilder();
    private static Library instance;

    private final ILogger logger;
    private final PlatformMethods methods;
    private final File folder;

    @Getter
    @Setter
    private Message noPermissionMessage, playerOnlyMessage, consoleOnlyMessage, internalErrorMessage, playerNotFoundMessage;

    @Getter
    @Setter
    private Message argInvalidInteger, argInvalidDouble, argInvalidBoolean, argInvalidTimeUnit;

    public Library(ILogger logger, PlatformMethods methods, File folder) {
        this.logger = logger;
        this.methods = methods;
        this.folder = folder;

        File libs = new File(folder, "libs");
        if(!libs.exists()){
            libs.mkdirs();
        }

        instance = this;
    }

    public static Library get(){
        if(instance == null) throw new IllegalStateException("Library doesn't initialized yet");
        return instance;
    }
}
