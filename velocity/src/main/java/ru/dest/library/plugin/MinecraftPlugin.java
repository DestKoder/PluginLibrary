package ru.dest.library.plugin;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dest.library.ITaskManager;
import ru.dest.library.command.ModernRegistrar;
import ru.dest.library.config.BaseConfig;
import ru.dest.library.ioc.UseAutoRegistration;
import ru.dest.library.lang.Lang;
import ru.dest.library.logging.ILogger;
import ru.dest.library.logging.SimpleLogger;
import ru.dest.library.module.LibraryModules;
import ru.dest.library.module.Module;
import ru.dest.library.velocity.VelocityRegistry;
import ru.dest.library.velocity.VelocityTaskManager;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.function.Consumer;

@Getter
public abstract class MinecraftPlugin<T extends MinecraftPlugin<T, CFG>, CFG extends BaseConfig> implements IPlugin<CFG>{


    private static final Logger log = LoggerFactory.getLogger(MinecraftPlugin.class);
    private final ProxyServer server;
    private final ILogger logger;
    private final File dataFolder;

    private IRegistry<T> registry;
    private ITaskManager taskManager;
    private Lang lang;
    private CFG config;


    @Inject
    @Contract(pure = true)
    public MinecraftPlugin(@DataDirectory @NotNull Path data, @NotNull Logger logger, ProxyServer server){
        Consumer<String> info = logger::info, warning = logger::warn, error = logger::error;
        this.logger = new SimpleLogger(info, warning, error);
        this.dataFolder = data.toFile();
        this.server =server;

        try{
            this.reload();
        }catch (Exception e){
            this.logger.error(e);
        }
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        if(!dataFolder.exists()) dataFolder.mkdir();

        try{
            if(getClass().isAnnotationPresent(RequireModules.class)){
                RequireModules modules = getClass().getAnnotation(RequireModules.class);
                for(Class<? extends Module> cl : modules.modules()){
                    if(!LibraryModules.isRegistered(cl)){
                        logger().warning("This plugin require " + cl + " module for work. Disabling...");
                        return;
                    }
                }
            }


            this.registry = new VelocityRegistry<T>((T)this);
            this.taskManager = new VelocityTaskManager<T>((T)this);

            if(this.getClass().isAnnotationPresent(UseAutoRegistration.class)) ((ModernRegistrar<T>)this.registry).onPluginEnable();
        }catch (Exception e){
            this.logger.warning("Error enabling " + getName() + ": " + e.getMessage());
            this.logger.error(e);
            return;
        }

        try{
            enable();
        }catch (Exception e){
            logger.error(e);
        }
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event){
        try{
            taskManager.cancelAll();
            disable();
        }catch (Exception e){
            logger.error(e);
        }
    }


    @Override
    public void reload() throws Exception {
        File configFile = new File(getDataFolder(), "config.yml");
        try {
            this.config = configClass().getDeclaredConstructor(File.class).newInstance(configFile);
            InputStream check  = getResource("config.yml");
            if(!configFile.exists() && check != null){
                System.out.println("Config file " + configFile.getPath() + " not found! ");
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
                this.lang = null;
            }
        }
        try{
            load();
        }catch (Exception e){
            logger.warning("Error occupied during loading state");
            logger.error(e);
        }
    }

    public InputStream getResource(String resource) {
        try {
            URL url = getClass().getClassLoader().getResource(resource);
            if (url == null) {
                return null;
            } else {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (IOException var4) {
            return null;
        }
    }

    public void saveResource(String resourcePath, boolean replace){
        if (resourcePath != null && !resourcePath.equals("")) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = this.getResource(resourcePath);
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found");
            } else {
                File outFile = new File(this.dataFolder, resourcePath);
                int lastIndex = resourcePath.lastIndexOf(47);
                File outDir = new File(this.dataFolder, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }

                try {
                    if (outFile.exists() && !replace) {
                        this.logger.warning("Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
                    } else {
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];

                        int len;
                        while((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }
                } catch (IOException var10) {
                    IOException ex = var10;
                    this.logger.warning("Could not save " + outFile.getName() + " to " + outFile + ":" + ex.getMessage());
                    this.logger.error(ex);
                }

            }
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }


    @Override
    public @NotNull String getName() {
        return getClass().getAnnotation(Plugin.class).name();
    }

    @Override
    public @NotNull String getAuthor() {
        String[] authors = getClass().getAnnotation(Plugin.class).authors();
        if(authors.length > 0){
            return authors[0];
        }
        return "Unknown";
    }

    @Override
    public @NotNull String getVersion() {
        return getClass().getAnnotation(Plugin.class).version();
    }

    @Override
    public @NotNull File getDataFolder() {
        return dataFolder;
    }

    @Override
    public @NotNull IRegistry<T> registry() {
        return registry;
    }

    @Override
    public @NotNull ILogger logger() {
        return this.logger;
    }

    @Override
    public ITaskManager taskManager() {
        return taskManager;
    }

    @Override
    public Lang lang() {
        return lang;
    }

    @Override
    public CFG config() {
        return config;
    }
}
