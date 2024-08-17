package ru.dest.library.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseEntity {

    public BaseEntity(ResultSet rs) throws SQLException {}
}
