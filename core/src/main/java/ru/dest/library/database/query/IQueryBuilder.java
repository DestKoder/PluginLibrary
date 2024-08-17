package ru.dest.library.database.query;

import java.util.concurrent.CompletableFuture;

public interface IQueryBuilder<T> {

    CompletableFuture<T> execute(Object... data);
    CompletableFuture<T> executeMany(Object[][] data);
}
