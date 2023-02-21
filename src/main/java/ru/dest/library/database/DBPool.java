package ru.dest.library.database;

import ru.dest.library.config.DatabaseConfig;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class DBPool {

    private final DBProvider connectionProvider;

    private final Vector<Connection> available = new Vector<>();
    private final Vector<Connection> used = new Vector<>();

    public DBPool(DatabaseConfig config) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, SQLException {
        this.connectionProvider = config.createProvider();

        for(int i = 0; i < config.getPoolSize(); i ++){
            available.add(connectionProvider.connect());
        }
    }

    public synchronized Connection retrieve() throws SQLException {
        Connection newConn = null;
        if (available.size() == 0) {
            newConn = connectionProvider.connect();
        } else {
            newConn = (Connection) available.lastElement();
            available.removeElement(newConn);
        }
        used.addElement(newConn);
        return newConn;
    }

    public synchronized void putback(Connection c) throws NullPointerException {
        if (c != null) {
            if (used.removeElement(c)) {
                available.addElement(c);
            } else {
                throw new NullPointerException("Connection not in the usedConnections array");
            }
        }
    }

    public void closeAll() throws SQLException {
        for(Connection conn : used) putback(conn);

        for(Connection connection : available) {
            connection.close();
        }

        available.clear();
    }
}
