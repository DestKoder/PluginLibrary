package ru.dest.library.database;

import ru.dest.library.config.DatabaseConfig;
import ru.dest.library.database.tasks.QueryTask;
import ru.dest.library.database.tasks.UpdateTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Logger;

public class Database extends DBPool{

    private final Logger logger;

    public Database(DatabaseConfig config, Logger logger) throws Exception {
        super(config);
        this.logger = logger;
    }

    public int update(String sql, Object[] data){
        Connection connection = null;
        try{
            connection = retrieve();
            FutureTask<Integer> task = new FutureTask<>(new UpdateTask(sql, data, connection));

            new Thread(task).start();

            return task.get();
        } catch (SQLException | InterruptedException | ExecutionException e) {
            logger.warning("Error occupied during update task");
            e.printStackTrace();
            return -1;
        }finally {
            putback(connection);
        }
    }
    private static final ResultSetHandler<Integer> idReturn = new ResultSetHandler<Integer>() {
        @Override
        public Integer handle(ResultSet rs) throws SQLException {
            rs.next();
            return rs.getInt("id");
        }
    };

    public int insertWithIdReturn(String sql, String table, Object[] data) {
        update(sql, data);
        return query("SELECT `id` FROM `"+table+"` ORDER BY `id` DESC LIMIT 1;", data, idReturn);
    }

    public <T> T query(String sql, Object[] data, ResultSetHandler<T> rsh) {
        Connection connection = null;
        try{
            connection = retrieve();
            FutureTask<T> task = new FutureTask<>(new QueryTask<T>(connection, sql, data, rsh));

            new Thread(task).start();

            return task.get();
        } catch (SQLException | InterruptedException | ExecutionException e) {
            logger.warning("Error occupied during update task");
            e.printStackTrace();
            return null;
        }finally {
            putback(connection);
        }
    }
}
