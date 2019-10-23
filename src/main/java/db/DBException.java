package db;


/**
 * Class for error handling.
 *
 * @author Baturo Valery
 * @version 1.0
 */
public class DBException extends Exception {
    public DBException(Throwable throwable) {
        super(throwable);
    }
}
