package ru.dest.library.database;

import ru.dest.library.database.provider.ConnectionProvider;
import ru.dest.library.dependency.RuntimeDependency;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DatabaseDriver {
    POSTGRESQL("org.postgresql.Driver", "https://dk-develop.ru/lib/driver/postgresql", "ru.dest.library.database.provider.PostgreSQLProvider"),
    MYSQL("com.mysql.cj.jdbc.Driver", "https://dk-develop.ru/lib/driver/mysql", "ru.dest.library.database.provider.MySQLProvider"),
    MARIADB("org.mariadb.jdbc.Driver", "https://dk-develop.ru/lib/driver/mariadb", "ru.dest.library.database.provider.MariadbProvider"),
    H2("org.h2.Driver", "https://dk-develop.ru/lib/driver/h2", "ru.dest.library.database.provider.H2LocalProvider"),
    H2_REMOTE("org.h2.Driver", "https://dk-develop.ru/lib/driver/h2", "ru.dest.library.database.provider.H2RemoteProvider"),
    SQLITE("org.sqlite.JDBC", "https://dk-develop.ru/lib/driver/sqlite", "ru.dest.library.database.provider.SQLiteProvider");
    ;
    private final String clName;
    private final String downloadUrl;
    private final String connectionProvider;

    DatabaseDriver(String clName, String downloadUrl, String connectionProvider) {
        this.clName = clName;
        this.downloadUrl = downloadUrl;
        this.connectionProvider = connectionProvider;
    }

    public static DatabaseDriver byName(String name){
        for(DatabaseDriver driver : values()){
            if(driver.name().equalsIgnoreCase(name)) return driver;
        }
        return null;
    }

    public ConnectionProvider createProvider(ConnectionProperties properties) throws Exception{
        return (ConnectionProvider) Class.forName(connectionProvider).getDeclaredConstructor(ConnectionProperties.class).newInstance(properties);
    }


    public void load() throws Exception {
        RuntimeDependency.loadIfAbsent(clName, downloadUrl).ifPresent(loader -> {
            try {
                Driver d = (Driver) loader.loadClass(clName).newInstance();
                DriverManager.registerDriver(new DriverWrap(d));
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
