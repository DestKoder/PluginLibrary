package ru.dest.library.database;

import ru.dest.library.database.provider.ConnectionProvider;
import ru.dest.library.database.provider.H2LocalProvider;
import ru.dest.library.database.provider.SQLiteProvider;
import ru.dest.library.database.query.InsertQuery;
import ru.dest.library.logging.ILogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;

/**
 * Database worker class
 *
 * @author DestKoder
 * @since 1.0
 */
public class Database {

    private final ConnectionProvider provider;
    private final ILogger logger;
    private final int poolSize;

    private Vector<Connection> available, used;
    private Connection connection;

    public Database(ConnectionProperties props, ILogger logger) throws Exception {
        this.provider = props.create();
        this.poolSize = props.getPoolSize();
        this.available = used = new Vector<>();
        this.logger = logger;
    }

    /**
     * Method to connect to database & test connection
     * @throws SQLException if error occupied
     */
    public void connect() throws SQLException {
        if(poolSize < 2 || provider instanceof SQLiteProvider || provider instanceof H2LocalProvider){
            connection = provider.connect();
            return;
        }
        for(int i = 0; i < poolSize; i++){
            this.available.add(provider.connect());
        }
    }

    /**
     * Get a connection from pool
     * @return free {@link Connection} to db
     * @throws SQLException if error occupied
     */
    public Connection retrieve() throws SQLException{
        if(poolSize < 2 || provider instanceof SQLiteProvider || provider instanceof H2LocalProvider){
            if(connection == null ) connection = provider.connect();;
            return connection;
        }
        Connection newConn = null;
        if (available.isEmpty()) {
            newConn = provider.connect();
        } else {
            newConn = (Connection) available.lastElement();
            available.removeElement(newConn);
        }
        used.addElement(newConn);
        return newConn;
    }

    /**
     * Return connection to pool
     * @param c {@link Connection} which was taken by {@link Database::retrieve}
     */
    public void putback(Connection c){
        if(poolSize < 2 || provider instanceof SQLiteProvider || provider instanceof H2LocalProvider) return;
        if (c != null) {
            if (used.removeElement(c)) {
                available.addElement(c);
            } else {
                throw new IllegalArgumentException("Connection not in the usedConnections array");
            }
        }
    }

    private void fill(PreparedStatement stmt, Object[] data) throws SQLException {
        for(int i = 0; i < data.length; i ++){
            stmt.setObject(i+1, data[i]);
        }
    }

    public InsertQuery insert(String table){
        return new InsertQuery(this, table);
    }


    /**
     * Run UPDATE / DELETE / INSERT query to database;
     * @param sql query to run
     * @param data data to insert into query
     * @return {@link CompletableFuture<Integer>} which completes with rows amount if success or -1 if error
     */
    public CompletableFuture<Integer> executeUpdate(String sql, Object... data) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            try{
                Connection conn = retrieve();
                PreparedStatement stmt = conn.prepareStatement(sql);
                fill(stmt, data);
                int rows = stmt.executeUpdate();
                stmt.close();
                putback(conn);
                future.complete(rows);
            }catch (SQLException e){
                logger.warning("Couldn't update/insert/delete: " + e.getMessage());
                logger.error(e);
                future.complete(-1);
            }
        });

        return future;
    }

    /**
     * Query many rows
     * @param sql SQL query
     * @param handler worker with {@link ResultSet}
     * @param data data to insert into query
     * @return {@link CompletableFuture<T>} which completes with {@link ArrayList} containing found rows
     * @param <T> Type of object to receive;
     */
    public <T> CompletableFuture<List<T>> queryMany(String sql, ResultSetHandler<T> handler, Object... data){
        CompletableFuture<List<T>> future = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            try{
                Connection conn = retrieve();
                PreparedStatement stmt = conn.prepareStatement(sql);
                fill(stmt, data);
                ResultSet rs = stmt.executeQuery();
                List<T> result = new ArrayList<>();

                while (rs.next()){
                    result.add(handler.handle(rs));
                }
                rs.close();
                stmt.close();
                putback(conn);
                future.complete(result);
            }catch (SQLException e){
                logger.warning("Couldn't update/insert/delete: " + e.getMessage());
                logger.error(e);
                future.complete(new ArrayList<>());
            } catch (Exception e) {
                logger.warning("Couldn't query: " + e.getMessage());
                logger.error(e);
                future.complete(new ArrayList<>());
            }
        });

        return future;
    }

    /**
     * Query one row
     * @param sql SQL query
     * @param handler worker with {@link ResultSet}
     * @param data data to insert into query
     * @return {@link CompletableFuture<T>} which completes with a founded row
     * @param <T> Type of object to receive;
     */
    public  <T> CompletableFuture<Optional<T>> queryOne(String sql, ResultSetHandler<T> handler, Object... data) {
        CompletableFuture<Optional<T>> future = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            try{
                Connection conn = retrieve();
                PreparedStatement stmt = conn.prepareStatement(sql);
                fill(stmt, data);
                ResultSet rs = stmt.executeQuery();
                Optional<T> result;

                if(!rs.next()) result = Optional.empty();
                else result = Optional.of(handler.handle(rs));

                rs.close();
                stmt.close();
                putback(conn);

                future.complete(result);
            }catch (SQLException e){
                logger.warning("Couldn't query via database error: " + e.getMessage());
                logger.error(e);
                future.complete(Optional.empty());
            } catch (Exception e) {
                logger.warning("Couldn't query: " + e.getMessage());
                logger.error(e);
                future.complete(Optional.empty());
            }
        });
        return future;
    }

    /**
     * Query one row
     * @param sql SQL query
     * @param baseEntityClass class which extends {@link BaseEntity}
     * @param data data to insert into query
     * @return {@link CompletableFuture<T>} which completes with a founded row
     * @param <T> Type of object to receive;
     */
    public  <T extends BaseEntity> CompletableFuture<Optional<T>> queryOne(String sql, Class<T> baseEntityClass, Object... data) {
        return queryOne(sql, (rs) -> { return baseEntityClass.getDeclaredConstructor(ResultSet.class).newInstance(rs);}, data);
    }

    /**
     * Query many rows
     * @param sql SQL query
     * @param baseEntityClass class which extends {@link BaseEntity}
     * @param data data to insert into query
     * @return {@link CompletableFuture<T>} which completes with {@link ArrayList} containing found rows
     * @param <T> Type of object to receive;
     */
    public <T extends BaseEntity> CompletableFuture<List<T>> queryMany(String sql, Class<T> baseEntityClass, Object... data){
        return queryMany(sql, (rs) -> { return baseEntityClass.getDeclaredConstructor(ResultSet.class).newInstance(rs);}, data);
    }


}
