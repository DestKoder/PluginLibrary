package ru.dest.library.database.dao;


import java.util.List;

public abstract class AbstractDAO<T> {

    public abstract void add(T t);
    public abstract T getById();
    public abstract List<T> getAll();
    public abstract void remove(T t);

    public void delete(T t){remove(t);}
}
