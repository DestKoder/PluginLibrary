package ru.dest.library.database.provider;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.config.DatabaseConfig;
import ru.dest.library.database.DBProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLProvider implements DBProvider {

    private final String LINK, USER, PASS;
    //jdbc:mysql://localhost:3306/database
    public MySQLProvider(@NotNull DatabaseConfig config) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.LINK = "jdbc:mysql://"+config.getAddress()+":"+config.getPort()+"/"+config.getDatabase()+"?autoReconnect=true"+(config.getParams() == null ? "" : "&" + config.getParams());
        this.USER = config.getUsername();
        this.PASS = config.getPassword();
    }

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(LINK, USER, PASS);
    }
}
