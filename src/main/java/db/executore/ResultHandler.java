package db.executore;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Generic for processing ResultSet
 *
 * @author Baturo Valery
 * @version 1.0
 */
public interface ResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}

