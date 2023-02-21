package ru.dest.library.database.dao;

import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBObject {

    private final int id;

    public DBObject(int id) {
        this.id = id;
    }

    public DBObject(@NotNull ResultSet row, String idColumn) throws SQLException {
        this.id = row.getInt(idColumn);
    }

    public int getId() {
        return id;
    }
}
