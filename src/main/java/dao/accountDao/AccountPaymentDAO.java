package dao.accountDao;


import models.account.AccountPayment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import db.executore.Executor;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AccountPaymentDAO extends AbstractJdbcDao<AccountPayment> {

    /**
     * Event Logger for class AccountPaymentDAO {@value}.
     */
    private static final Logger log = LogManager.getLogger(AccountPaymentDAO.class.getName());

    /**
     * Object for working with the base.
     */
    private Executor executor;

    /**
     * The connection to database.
     *
     * @param connection - the connection to database.
     */
    public AccountPaymentDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    /**
     * Returns  sql query to retrieve all records.
     * SELECT * FROM [Table]
     *
     * @return sql query
     */
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM accounts.account_payment ";
    }

    /**
     * Returns  sql query to update a record from the database.
     * UPDATE [Table]  SET idAccountPayment= ?";
     *
     * @return sql query
     */
    @Override
    public String getUpdateQuery() {
        return "UPDATE accounts.account_payment SET ";
    }

    /**
     * Returns  sql query to insert a new record into the database.
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     *
     * @return sql query
     */
    @Override
    public String getCreateQuery() {
        return "INSERT INTO accounts.account_payment ";
    }

    /**
     * Returns  sql query to remove a record from the database.
     * DELETE FROM [Table] WHERE idAccountPayment= ?;
     *
     * @return sql query
     */
    @Override
    public String getDeleteQuery() {
        return "DELETE FROM accounts.account_payment WHERE idAccountPayment='";
    }

    /**
     * Returns  sql query to remove a record from the database by ID Customer.
     * DELETE FROM [Table] WHERE idCustomer= ?;
     *
     * @return sql query
     */
    @Override
    public String getDeleteQueryCustomer() {
        return "DELETE FROM accounts.account_payment WHERE idCustomer='";
    }

    /**
     * Returns an account from database by id
     *
     * @param id account number
     * @return account with the specified id or null if not found
     */
    @Override
    public AccountPayment getAccountById(long id) {
        try {
            return executor.execQuery(getSelectQuery() + "WHERE idAccountPayment='" + id + "'", resultSet -> {
                resultSet.next();
                return new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
            });
        } catch (SQLException e) {
            log.error(e.toString());
            return null;
        }
    }

    /**
     * Remove an account from database by id
     *
     * @param id account number
     */
    @Override
    public void deleteAccountById(long id) {
        executor.execUpdate((getDeleteQuery() + id + "'"));
    }

    /**
     * Remove an account from database by id
     *
     * @param idCustomer customer number
     */
    @Override
    public void deleteAccountByIdCustomer(long idCustomer) {
        executor.execUpdate((getDeleteQueryCustomer() + idCustomer + "'"));
    }

    /**
     * Block ro unblock an account from database by id
     *
     * @param id      account number
     * @param blocked 0 or 1. 0 - unblocked account, 1 - blocked account
     * @throws SQLException database error
     */
    @Override
    public void setBlockedAccount(long id, byte blocked) throws SQLException {
        executor.execUpdate(getUpdateQuery() + "blocked='" + blocked + "' WHERE idAccountPayment='" + id + "'");
    }

    /**
     * Get account balance by id
     *
     * @param id account number
     * @return account balance
     * @throws SQLException database error
     */
    @Override
    public double getAccountBalanceById(long id) throws SQLException {
        return executor.execQuery(getSelectQuery() + "WHERE idAccountPayment='" + id + "'", resultSet -> {
            resultSet.next();
            return resultSet.getDouble(3);
        });
    }

    /**
     * Get all customer accounts by his id
     *
     * @param idCustomer customer number
     * @return list of accounts customer
     * @throws SQLException database error
     */
    @Override
    public List<AccountPayment> getAccountByIdCustomer(long idCustomer) throws SQLException {
        List<AccountPayment> accountPayments = new ArrayList<>();
        executor.execQuery(getSelectQuery() + "WHERE idCustomer='" + idCustomer + "'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });
        return accountPayments;
    }

    /**
     * Get all block or unblock customer accounts by his id
     *
     * @param idCustomer customer number
     * @param blocked    0 or 1. 0 - unblocked account, 1 - blocked account
     * @return list of accounts customer
     * @throws SQLException database error
     */
    @Override
    public List<AccountPayment> getAccountBlockedOrUnBlocked(long idCustomer, byte blocked) throws SQLException {
        List<AccountPayment> accountPayments = new ArrayList<>();

        executor.execQuery(getSelectQuery() + " WHERE idCustomer='" + idCustomer + "'" + " AND blocked='" + blocked + "'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });
        return accountPayments;
    }

    /**
     * Get all customer accounts by his id for the period
     *
     * @param idCustomer customer number
     * @param dateStart  beginning of period
     * @param dateEnd    end of period
     * @return list of accounts customer
     * @throws SQLException database error
     */
    @Override
    public List<AccountPayment> getAccountByIdCustomerSelectDate(long idCustomer, Date dateStart, Date dateEnd) throws SQLException {
        List<AccountPayment> accountPayments = new ArrayList<>();
        executor.execQuery(getSelectQuery() + "WHERE idCustomer='" + idCustomer + "'" + " AND dateOpen>='" + dateStart + "'" + " AND dateOpen<='" + dateEnd + "'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });
        return accountPayments;
    }

    /**
     * Get all customer accounts by his id more than the specified amount
     *
     * @param idCustomer customer number
     * @param sum        amount
     * @return list of accounts customer
     * @throws SQLException database error
     */
    @Override
    public List<AccountPayment> getAccountMoreThan(long idCustomer, double sum) throws SQLException {
        List<AccountPayment> accountPayments = new ArrayList<>();
        executor.execQuery(getSelectQuery() + "WHERE idCustomer='" + idCustomer + "'" + " AND balance>='" + sum + "'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });
        return accountPayments;
    }


    /**
     * Check if the client has accounts
     *
     * @param idCustomer customer number
     * @return true if account exist and false otherwise
     * @throws SQLException database error
     */
    @Override
    public boolean existAccount(long idCustomer) throws SQLException {
        return executor.execQuery(getSelectQuery() + "WHERE idCustomer='" + idCustomer + "'", resultSet -> {
            int size = 0;
            if (resultSet != null) {
                resultSet.last();
                size = resultSet.getRow();
            }
            if (size != 0) return true;
            else
                return false;
        });
    }

    /**
     * Check if there is an account
     *
     * @param idAccountPayment account number
     * @return true if account exist and false otherwise
     * @throws SQLException database error
     */
    @Override
    public boolean existAccountById(long idAccountPayment) throws SQLException {
        return executor.execQuery(getSelectQuery() + "WHERE idAccountPayment='" + idAccountPayment + "'", resultSet -> {
            int size = 0;
            if (resultSet != null) {
                resultSet.last();
                size = resultSet.getRow();
            }
            if (size != 0) return true;
            else
                return false;
        });
    }

    /**
     * Check Is account blocked
     *
     * @param idAccountPayment account number
     * @return true if account blocked and false otherwise
     * @throws SQLException database error
     */
    @Override
    public boolean checkBlockAccount(long idAccountPayment) throws SQLException {
        return executor.execQuery(getSelectQuery() + "WHERE idAccountPayment='" + idAccountPayment + "' AND blocked='1'", resultSet -> {

            int size = 0;
            if (resultSet != null) {
                resultSet.last();
                size = resultSet.getRow();
            }
            if (size != 0) return true;
            else
                return false;
        });
    }

    /**
     * Change account amount
     *
     * @param idAccountPayment account number
     * @param sum              amount
     * @throws SQLException error with database
     */
    @Override
    public void updateAccountBalance(long idAccountPayment, double sum) throws SQLException {
        executor.execUpdate((getUpdateQuery() + "balance= balance+'" + sum + "' WHERE idAccountPayment='" + idAccountPayment + "'"));

    }

    /**
     * Add account into database
     *
     * @param blocked    account blocked or not
     * @param balance    amount account
     * @param idCustomer customer number
     * @param date       account opening date
     * @throws SQLException error with database
     */
    public void addAccount(byte blocked, double balance, long idCustomer, Date date) throws SQLException {
        executor.execUpdate(getCreateQuery() + "(blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('" + blocked + "', '" + balance + "', '" + idCustomer + "', '" + date + "')");
    }


}


