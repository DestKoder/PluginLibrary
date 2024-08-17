package ru.dest.library.database.provider;

import ru.dest.library.database.ConnectionProperties;
import ru.dest.library.database.DatabaseDriver;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteProvider extends ConnectionProvider{

    private final String url;

    public SQLiteProvider(ConnectionProperties props) throws Exception {
        super(props);
        DatabaseDriver.SQLITE.load();

        this.url = "jdbc:sqlite:"+ new File(props.getRootDir(), props.getBase() == null ? "database" : props.getBase() + ".db");
    }

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
