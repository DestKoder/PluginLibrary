package ru.dest.library.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ResultSetHandler<T> {
    public abstract T handle(ResultSet rs) throws SQLException;
}
