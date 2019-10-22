package dao.accountDao;


import java.sql.SQLException;
import java.util.*;

/**
 * Abstract class for working with a database
 * @autor Baturo Valery
 * @version 1.0
 */

public abstract class AbstractJdbcDao<T> {

    /**
     * Returns  sql query to retrieve all records.
     * <p/>
     * SELECT * FROM [Table]
     */
    public abstract String getSelectQuery();

    /**
     * Returns  sql query to insert a new record into the database.
     * <p/>
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     */
    public abstract String getCreateQuery();

    /**
     * Returns  sql query to remove a record from the database.
     * <p/>
     * DELETE FROM [Table] WHERE idAccountPayment= ?;
     */
    public abstract String getDeleteQuery();

    /**
     * Returns  sql query to update a record from the database.
     * <p/>
     * UPDATE [Table]  SET idAccountPayment= ?";
     */
    public abstract String getUpdateQuery ();

    /**
     * Returns  sql query to remove a record from the database by ID Customer.
     * <p/>
     * DELETE FROM [Table] WHERE idCustomer= ?;
     */
    public abstract String  getDeleteQueryCustomer() throws  SQLException;

    /**
     * Returns an account from database by id
     * <p/>
     * @param id - account number
     */
    public abstract T getAccountById(long id) throws SQLException;

    /**
     * Remove an account from database by id
     * <p/>
     * @param id - account number
     */
    public abstract void deleteAccountById(long id) throws SQLException;

    /**
     * Remove an account from database by id
     * <p/>
     * @param idCustomer - customer number
     */
    public abstract void deleteAccountByIdCustomer(long idCustomer) throws SQLException;

    /**
     * Block ro unblock an account from database by id
     * <p/>
     * @param id - account number
     * @param blocked - 0 or 1. 0 - unblocked account, 1 - blocked account
     */
    public abstract  void setBlockedAccount(long id,byte blocked) throws SQLException;

    /**
     * Get account balance by id
     * <p/>
     * @param id - account number
     */
    public abstract  double getAccountBalanceById(long id) throws SQLException;

    /**
     *Get all customer accounts by his id
     * <p/>
     * @param idCustomer - customer number
     */
    public abstract List<T> getAccountByIdCustomer(long idCustomer) throws SQLException;

    /**
     *Get all block or unblock customer accounts by his id
     * <p/>
     * @param idCustomer - customer number
     * @param blocked - 0 or 1. 0 - unblocked account, 1 - blocked account
     */
    public abstract List<T> getAccountBlockedOrUnBlocked(long idCustomer, byte blocked) throws SQLException;

    /**
     *Get all customer accounts by his id for the period
     * <p/>
     * @param idCustomer - customer number
     * @param dateStart -beginning of period
     * @param dateEnd -end of period
     */
    public abstract List<T> getAccountByIdCustomerSelectDate(long idCustomer, Date dateStart, Date dateEnd) throws SQLException;

    /**
     *Get all customer accounts by his id more than the specified amount
     * <p/>
     * @param idCustomer - customer number
     * @param sum -amount
     */
    public abstract List<T> getAccountMoreThan(long idCustomer, double sum) throws SQLException;

    /**
     *Check if the client has accounts
     * <p/>
     * @param idCustomer - customer number
     */
    public abstract boolean existAccount(long idCustomer) throws SQLException;

    /**
     *Check if there is an account
     * <p/>
     * @param idAccountPayment - account number
     */
    public abstract boolean existAccountById(long idAccountPayment) throws SQLException;

    /**
     *Check Is account blocked
     * <p/>
     * @param idAccountPayment - account number
     */
    public abstract boolean checkBlockAccount(long idAccountPayment) throws SQLException;

    /**
     *Change account amount
     * <p/>
     * @param idAccountPayment - account number
     * @param sum -amount
     */
    public abstract void updateAccountBalance(long idAccountPayment, double  sum) throws SQLException;

}
