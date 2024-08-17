package ru.dest.library.database.provider;

import ru.dest.library.database.ConnectionProperties;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ConnectionProvider {

    public ConnectionProvider(ConnectionProperties props){}

    public abstract Connection connect() throws SQLException;

}
