package ru.dest.library.database.provider;

import ru.dest.library.config.DatabaseConfig;
import ru.dest.library.database.DBProvider;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2LocalProvider implements DBProvider {

    private final String LINK;

    public H2LocalProvider(DatabaseConfig config) throws ClassNotFoundException {
        File f = new File(config.getPlugin().getDataFolder(), config.getDatabase());
        this.LINK = "jdbc:h2:"+f;

        Class.forName("org.h2.Driver");
    }

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(LINK, "ab", "ab");
    }

}
