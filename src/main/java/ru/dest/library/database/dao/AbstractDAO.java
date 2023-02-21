package ru.dest.library.database.dao;


import java.util.List;

public abstract class AbstractDAO<T> {

    public abstract void add(T t);
    public abstract T getById(int id);
    public abstract List<T> getAll();
    public abstract void remove(T t);
    public abstract void remove(int id);

    public void delete(T t){remove(t);}
    public void delete(int id){remove(id);}
}
