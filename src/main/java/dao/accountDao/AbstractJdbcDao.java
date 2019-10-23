package dao.accountDao;


import java.sql.SQLException;
import java.util.*;

/**
 * Abstract class for working with a database
 *
 * @author Baturo Valery
 * @version 1.0
 */
public abstract class AbstractJdbcDao<T> {

    /**
     * Returns  sql query to retrieve all records.
     * SELECT * FROM [Table]
     *
     * @return sql query
     */
    public abstract String getSelectQuery();

    /**
     * Returns  sql query to insert a new record into the database.
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     *
     * @return sql query
     */
    public abstract String getCreateQuery();

    /**
     * Returns  sql query to remove a record from the database.
     * DELETE FROM [Table] WHERE idAccountPayment= ?;
     *
     * @return sql query
     */
    public abstract String getDeleteQuery();

    /**
     * Returns  sql query to update a record from the database.
     * UPDATE [Table]  SET idAccountPayment= ?";
     *
     * @return sql query
     */
    public abstract String getUpdateQuery();

    /**
     * Returns  sql query to remove a record from the database by ID Customer.
     * DELETE FROM [Table] WHERE idCustomer= ?;
     *
     * @return sql query
     */
    public abstract String getDeleteQueryCustomer();

    /**
     * Returns an account from database by id
     *
     * @param id account number
     * @return account with the specified id or null if not found
     */
    public abstract T getAccountById(long id);

    /**
     * Remove an account from database by id
     *
     * @param id account number
     */
    public abstract void deleteAccountById(long id);

    /**
     * Remove an account from database by id
     *
     * @param idCustomer customer number
     */
    public abstract void deleteAccountByIdCustomer(long idCustomer);

    /**
     * Block ro unblock an account from database by id
     *
     * @param id      account number
     * @param blocked 0 or 1. 0 - unblocked account, 1 - blocked account
     * @throws SQLException database error
     */
    public abstract void setBlockedAccount(long id, byte blocked) throws SQLException;

    /**
     * Get account balance by id
     *
     * @param id account number
     * @return account balance
     * @throws SQLException database error
     */
    public abstract double getAccountBalanceById(long id) throws SQLException;

    /**
     * Get all customer accounts by his id
     *
     * @param idCustomer customer number
     * @return list of accounts customer
     * @throws SQLException database error
     */
    public abstract List<T> getAccountByIdCustomer(long idCustomer) throws SQLException;

    /**
     * Get all block or unblock customer accounts by his id
     *
     * @param idCustomer customer number
     * @param blocked    0 or 1. 0 - unblocked account, 1 - blocked account
     * @return list of accounts customer
     * @throws SQLException database error
     */
    public abstract List<T> getAccountBlockedOrUnBlocked(long idCustomer, byte blocked) throws SQLException;

    /**
     * Get all customer accounts by his id for the period
     *
     * @param idCustomer customer number
     * @param dateStart  beginning of period
     * @param dateEnd    end of period
     * @return list of accounts customer
     * @throws SQLException database error
     */
    public abstract List<T> getAccountByIdCustomerSelectDate(long idCustomer, Date dateStart, Date dateEnd) throws SQLException;

    /**
     * Get all customer accounts by his id more than the specified amount
     *
     * @param idCustomer customer number
     * @param sum        amount
     * @return list of accounts customer
     * @throws SQLException database error
     */
    public abstract List<T> getAccountMoreThan(long idCustomer, double sum) throws SQLException;

    /**
     * Check if the client has accounts
     *
     * @param idCustomer customer number
     * @return true if account exist and false otherwise
     * @throws SQLException database error
     */
    public abstract boolean existAccount(long idCustomer) throws SQLException;

    /**
     * Check if there is an account
     *
     * @param idAccountPayment account number
     * @return true if account exist and false otherwise
     * @throws SQLException database error
     */
    public abstract boolean existAccountById(long idAccountPayment) throws SQLException;

    /**
     * Check Is account blocked
     *
     * @param idAccountPayment account number
     * @return true if account blocked and false otherwise
     * @throws SQLException database error
     */
    public abstract boolean checkBlockAccount(long idAccountPayment) throws SQLException;

    /**
     * Change account amount
     *
     * @param idAccountPayment account number
     * @param sum              amount
     * @throws SQLException error with database
     */
    public abstract void updateAccountBalance(long idAccountPayment, double sum) throws SQLException;

}
