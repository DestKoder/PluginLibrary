package ru.dest.library.database.provider;

import ru.dest.library.database.ConnectionProperties;
import ru.dest.library.database.DatabaseDriver;
import ru.dest.library.exception.InvalidConnectionPropertiesException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLProvider extends ConnectionProvider{

    private final String url, user, pass;

    public PostgreSQLProvider(ConnectionProperties props) throws Exception {
        super(props);
        if(props.getHost() == null || props.getUser() == null || props.getPassword() == null || props.getBase() == null) throw new InvalidConnectionPropertiesException();

        DatabaseDriver.POSTGRESQL.load();

        this.url = "jdbc:postgresql://"+props.getHost()+":"+props.getPort()+"/"+props.getBase()+"?autoReconnect=true"+(props.getParams() == null ? "" : "&"+props.getParams());
        this.user = props.getUser();
        this.pass = props.getPassword();
    }

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
