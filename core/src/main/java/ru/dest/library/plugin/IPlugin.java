package ru.dest.library.plugin;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.ITaskManager;
import ru.dest.library.config.BaseConfig;
import ru.dest.library.lang.Lang;
import ru.dest.library.logging.ILogger;

import java.io.File;

public interface IPlugin<CFG extends BaseConfig> {

    @NotNull
    String getName();
    @NotNull
    String getAuthor();
    @NotNull
    String getVersion();

    @NotNull
    File getDataFolder();

    @NotNull
    IRegistry<?> registry();
    @NotNull
    ILogger logger();

    ITaskManager taskManager();

    Lang lang();

    CFG config();

    void enable() throws Exception;
    void disable() throws Exception;
    void load() throws Exception;
    void reload() throws Exception;

    default boolean hasLang(){return false;}
    default String langFile() {return "";}


    Class<CFG> configClass();

}
