package ru.dest.library.database.provider;

import ru.dest.library.database.ConnectionProperties;
import ru.dest.library.database.DatabaseDriver;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2LocalProvider extends ConnectionProvider{

    private final String url;
    private final String user = "sa";
    private final String password = "";

    public H2LocalProvider(ConnectionProperties props) throws Exception {
        super(props);
        DatabaseDriver.H2.load();

        this.url = "dbc:h2:"+ new File(props.getRootDir(), props.getBase() == null ? "database" : props.getBase() + ".h2.db");
    }

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}
