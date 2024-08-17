package ru.dest.library.database.query;

import ru.dest.library.database.Database;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class InsertQuery implements IQueryBuilder<Integer>{

    private final Database database;

    private final Map<String, String> values;
    private final String table;

    public InsertQuery(Database database, String table) {
        this.database = database;
        this.values = new HashMap<>();
        this.table =table;
    }

    public InsertQuery value(String val){
        this.values.put(val, null);
        return this;
    }

    public InsertQuery value(String val, String value){
        this.values.put(val, value);
        return this;
    }

    public String buildQuery(){
        StringBuilder sb = new StringBuilder("INSERT INTO `" + table + "` (");
        for(String s : values.keySet()){
            sb.append('`');
            sb.append(s);
            sb.append('`');
            sb.append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(") VALUES (");
        for(String s : values.keySet()){
            if(values.get(s) == null) {
                sb.append('?');
            }else if(values.get(s).equalsIgnoreCase("null")) sb.append(values.get(s));
            else sb.append('\'').append(s).append('\'');
            sb.append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(");");
        return sb.toString();
    }

    @Override
    public CompletableFuture<Integer> execute(Object... data) {
        return database.executeUpdate(buildQuery(), data);
    }

    @Override
    public CompletableFuture<Integer> executeMany(Object[][] data) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        String sql = buildQuery();
        CompletableFuture.runAsync(() -> {
            int rows = 0;
            for (Object[] datum : data) {
                int r = database.executeUpdate(sql, datum).join();
                if (r == -1) {
                    future.complete(-1);
                    return;
                }
                rows += r;
            }
        });

        return future;
    }
}
