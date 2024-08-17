package ru.dest.library.database;

import java.sql.ResultSet;

public interface ResultSetHandler<T> {

    T handle(ResultSet rs ) throws Exception;

}
