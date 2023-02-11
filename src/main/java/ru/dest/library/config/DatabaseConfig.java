package ru.dest.library.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;
import ru.dest.library.database.DBProvider;
import ru.dest.library.database.DBType;
import ru.dest.library.exception.UnsupportedDatabaseException;

import java.lang.reflect.InvocationTargetException;

public class DatabaseConfig {

    private final String TYPE;
    private final String address, username, password, database, params;
    private  int port = 25565, poolSize = 0;
    private final Plugin plugin;

    public DatabaseConfig(ConfigurationSection config, Plugin plugin) throws InvalidConfigurationException {
        this.TYPE = config.getString("type");
        this.address = config.getString("address");
        this.username = config.getString("username");
        this.password = config.getString("password");
        this.database = config.getString("database");
        this.params = config.isSet("parameters") ? config.getString("parameters") : "";

        this.port = config.getInt("port");
        this.poolSize = config.getInt("poolSize");

        this.plugin = plugin;

        if(TYPE == null || database == null) throw new InvalidConfigurationException("Missing necessary config keys: type, database");
    }

    public DBProvider createProvider() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DBType t = DBType.getByName(TYPE);

        if(t == null) throw new UnsupportedDatabaseException(TYPE);

        return t.getProvider(this);
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public int getPort() {
        return port;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public String getParams() {
        return params;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
