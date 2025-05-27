package ru.dest.library.plugin;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.ITaskManager;
import ru.dest.library.bukkit.BukkitRegistry;
import ru.dest.library.bukkit.BukkitTaskManager;
import ru.dest.library.config.BaseConfig;
import ru.dest.library.lang.Lang;
import ru.dest.library.logging.ILogger;
import ru.dest.library.logging.SimpleLogger;
import ru.dest.library.module.LibraryModules;
import ru.dest.library.module.Module;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("unchecked")
public abstract class MinecraftPlugin<T extends MinecraftPlugin<T, CFG>, CFG extends BaseConfig> extends JavaPlugin implements IPlugin<CFG> {

    private ILogger logger;
    private IRegistry<T> registry;
    private ITaskManager taskManager;
    private Lang lang;
    private CFG config;

    @Override
    public final void onLoad() {
        this.logger = new SimpleLogger(getLogger());

        try{
            reload();
        }catch (Exception e){
            logger.warning("Error occupied during loading state");
            logger.error(e);
        }

    }

    @Override
    public final void onEnable() {
        try{
            if(getClass().isAnnotationPresent(RequireModules.class)){
                RequireModules modules = getClass().getAnnotation(RequireModules.class);
                for(Class<? extends Module> cl : modules.modules()){
                    if(!LibraryModules.isRegistered(cl)){
                        logger().warning("This plugin require " + cl + " module for work. Disabling...");
                        getServer().getPluginManager().disablePlugin(this);
                        return;
                    }
                }
            }

            this.registry = new BukkitRegistry<>((T)this);
            taskManager = new BukkitTaskManager(this);

            enable();
        }catch (Exception e){
            logger.warning("Error occupied during enabling plugin");
            logger.error(e);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void reload() throws Exception {
        File configFile = new File(getDataFolder(), "config.yml");
        try {
            this.config = configClass().getDeclaredConstructor(File.class).newInstance(configFile);
            InputStream check  =getResource("config.yml");
            if(!configFile.exists() && check != null){
                check.close();
                saveResource("config.yml", true);
            }
            config.load();
        } catch (Exception e) {
            logger.warning("Couldn't load config file: ");
            logger.error(e);
            config =null;
        }

        if(hasLang()){
            File lang = new File(getDataFolder(), langFile());
            if(!lang.exists()){
                saveResource(langFile(), true);
            }
            try {
                this.lang = Lang.load(lang);
            } catch (IOException e) {
                logger.warning("Couldn't load lang file " + langFile());
                logger.error(e);
                this.lang=null;
            }
        }
        try{
            load();
        }catch (Exception e){
            logger.warning("Error occupied during loading state");
            logger.error(e);
        }
    }

    @Override
    public final void onDisable() {
        HandlerList.unregisterAll(this);
        try{
            if(taskManager != null) taskManager.cancelAll();
            disable();
        }catch (Exception e){
            logger.warning("Error occupied during disabling plugin");
            logger.error(e);
        }
    }

    @Override
    public CFG config() {
        return config;
    }

    @Override
    public @NotNull String getAuthor() {
        return getDescription().getAuthors().get(0) == null ? "Unknown" : getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return getDescription().getVersion();
    }

    @Override
    public ITaskManager taskManager() {
        return taskManager;
    }

    @Override
    public @NotNull IRegistry<T> registry() {
        return registry;
    }

    @Override
    public @NotNull ILogger logger() {
        return logger;
    }

    @Override
    public Lang lang() {
        return lang;
    }
}
