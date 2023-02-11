package ru.dest.library.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBProvider {
    Connection connect() throws SQLException;
}
