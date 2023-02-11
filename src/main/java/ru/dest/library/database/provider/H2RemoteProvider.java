package ru.dest.library.database.provider;

import ru.dest.library.config.DatabaseConfig;
import ru.dest.library.database.DBProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2RemoteProvider implements DBProvider {

    private final String LINK, USER, PASS;

    public H2RemoteProvider(DatabaseConfig config) throws ClassNotFoundException {
        this.LINK = "jdbc:h2:tcp://"+config.getAddress()+"/"+config.getDatabase();
        this.USER = config.getUsername();
        this.PASS = config.getPassword();

        Class.forName("org.h2.Driver");
    }

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(LINK, USER, PASS);
    }
}
