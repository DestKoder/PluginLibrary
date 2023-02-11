package ru.dest.library.database.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Callable;

import static ru.dest.library.utils.Utils.fillStatement;

public class UpdateTask implements Callable<Integer> {
    private final String query;
    private final Connection connection;
    private final Object[] data;

    public UpdateTask(String query, Object[] data, Connection connection) {
        this.query = query;
        this.connection = connection;
        this.data = data;
    }

    @Override
    public Integer call() throws Exception {
        int result = -1;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            if(data != null){
                fillStatement(stmt, data);
            }

            result = stmt.executeUpdate();

            stmt.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return result;
    }
}
