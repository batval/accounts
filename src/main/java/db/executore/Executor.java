package db.executore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {

    private static final Logger log = LogManager.getLogger(Executor.class.getName());
    private final Connection connection;


    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String update)   {
       try {
           Statement stmt = connection.createStatement();
           stmt.execute(update);
           stmt.close();
       }
       catch (SQLException e)
       {
           log.error(e.toString());
       }
    }

    public <T> T execQuery(String query,
                           ResultHandler<T> handler)
            throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T value = handler.handle(result);
        result.close();
        stmt.close();

        return value;
    }

}
