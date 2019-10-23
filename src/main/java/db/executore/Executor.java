package db.executore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Сontaining methods for working with database queries.
 *
 * @author Baturo Valery
 * @version 1.0
 */
public class Executor {

    /**
     * Event Logger for class Executor {@value}.
     */
    private static final Logger log = LogManager.getLogger(Executor.class.getName());
    /**
     * The connection to database.
     */
    private final Connection connection;

    /**
     * Getting database connection.
     *
     * @param connection - the connection to database.
     */
    public Executor(Connection connection) {
        this.connection = connection;
    }

    /**
     * Processing requests for creating, inserting, updating, and deleting.
     *
     * @param update - sql request.
     */
    public void execUpdate(String update) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(update);
            statement.close();
        } catch (SQLException e) {
            log.error(e.toString());
        }
    }

    /**
     * Data request processing.
     *
     * @param query   - sql request.
     * @param handler - getting ResultSet.
     * @return ResultSet after executing sql request.
     * @throws SQLException - error with database.
     */
    public <T> T execQuery(String query,
                           ResultHandler<T> handler)
            throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(query);
        ResultSet result = statement.getResultSet();
        T value = handler.handle(result);
        result.close();
        statement.close();

        return value;
    }

}
