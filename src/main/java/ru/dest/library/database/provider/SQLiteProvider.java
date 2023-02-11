package ru.dest.library.database.provider;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.config.DatabaseConfig;
import ru.dest.library.database.DBProvider;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteProvider implements DBProvider {

    private final String LINK;

    public SQLiteProvider(@NotNull DatabaseConfig config) throws ClassNotFoundException {
        File f = new File(config.getPlugin().getDataFolder(), config.getDatabase()+".db");

        this.LINK = "jdbc:sqlite:"+f;

        Class.forName("org.sqlite.JDBC");
    }

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(LINK);
    }
}
