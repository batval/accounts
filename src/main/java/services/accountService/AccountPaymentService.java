package services.accountService;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import db.DBException;
import db.DBService;

import models.account.AccountPayment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.accountDao.AccountPaymentDAO;

/**
 * Class describes the behavior of the account payment service
 * implements AccountService
 *
 * @author Baturo Valery
 * @version 1.0
 */

public class AccountPaymentService implements AccountService {

    /**
     * Event Logger for class AccountPaymentService {@value}.
     */
    private static final Logger log = LogManager.getLogger(AccountPaymentService.class.getName());

    /**
     * Connection to database.
     */
    private final Connection connection;

    /**
     * Connection initialization
     *
     * @throws FileNotFoundException database error
     */
    public AccountPaymentService() throws FileNotFoundException {
        this.connection = new DBService().getMysqlConnection();
    }

    /**
     * Check if the client has accounts
     *
     * @param idCustomer customer number
     * @return true if account exist and false otherwise
     * @throws DBException database error
     */
    @Override
    public boolean existAccount(long idCustomer) throws DBException {
        try {
            return (new AccountPaymentDAO(connection).existAccount(idCustomer));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Check if there is an account
     *
     * @param idAccountPayment account number
     * @return true if account exist and false otherwise
     * @throws DBException database error
     */
    @Override
    public boolean existAccountById(long idAccountPayment) throws DBException {
        try {
            return (new AccountPaymentDAO(connection).existAccountById(idAccountPayment));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Check Is account blocked
     *
     * @param idAccountPayment account number
     * @return true if account blocked and false otherwise
     * @throws DBException database error
     */
    @Override
    public boolean checkBlockAccount(long idAccountPayment) throws DBException {
        try {
            return (new AccountPaymentDAO(connection).checkBlockAccount(idAccountPayment));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Add a payment account to the database
     *
     * @param blocked    account blocked or not
     * @param balance    amount account
     * @param idCustomer customer number
     * @param date       account opening date
     * @throws DBException error with database
     */
    public void addAccountPayment(byte blocked, double balance, long idCustomer, Date date) throws DBException {
        try {
            log.info("Add account with parameters: blocked-" + blocked + ", balance-" + balance + ", id customer-" + idCustomer + ", Open date- " + date);
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            apDAO.addAccount(blocked, balance, idCustomer, date);
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }


    /**
     * Remove an account from database by id
     *
     * @param idAccount account number
     */
    @Override
    public void deleteAccount(long idAccount) throws DBException {
        new AccountPaymentDAO(connection).deleteAccountById(idAccount);
    }

    /**
     * Remove all accounts from database by id customer
     *
     * @param idAccountCustomer customer number
     */
    @Override
    public void deleteAllAccountCustomer(long idAccountCustomer) throws DBException {
        if (existAccount(idAccountCustomer)) {
            log.info("Delete accounts for customer with id: " + idAccountCustomer);
            new AccountPaymentDAO(connection).deleteAccountByIdCustomer(idAccountCustomer);
        }
    }

    /**
     * Block or unblock an account from database by id
     *
     * @param idAccount account number
     * @param blocked   0 or 1. 0 - unblocked account, 1 - blocked account
     * @throws DBException database error
     */
    @Override
    public void setBlockedOrUnblocked(long idAccount, byte blocked) throws DBException {
        try {

            new AccountPaymentDAO(connection).setBlockedAccount(idAccount, blocked);

        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Returns an account from database by id
     *
     * @param idAccount account number
     * @return account with the specified id or null if not found
     */
    @Override
    public AccountPayment getAccount(long idAccount) {
        return (new AccountPaymentDAO(connection).getAccountById(idAccount));
    }

    /**
     * Get all customer accounts by his id
     *
     * @param idCustomer customer number
     * @return list of accounts customer
     * @throws DBException database error
     */
    public List<AccountPayment> getAccounts(long idCustomer) throws DBException {
        try {
            return (new AccountPaymentDAO(connection).getAccountByIdCustomer(idCustomer));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Get all block or unblock customer accounts by his id
     *
     * @param idCustomer customer number
     * @param blocked    0 or 1. 0 - unblocked account, 1 - blocked account
     * @return list of accounts customer
     * @throws DBException database error
     */
    @Override
    public List<AccountPayment> getAccountsBlockedOrUnBlocked(long idCustomer, byte blocked) throws DBException {
        try {
            return (new AccountPaymentDAO(connection).getAccountBlockedOrUnBlocked(idCustomer, blocked));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }

    }

    /**
     * Get all customer accounts by his id for the period
     *
     * @param idCustomer customer number
     * @param dateStart  beginning of period
     * @param dateEnd    end of period
     * @return list of accounts customer
     * @throws DBException database error
     */
    @Override
    public List<AccountPayment> getAccountsSelectDate(long idCustomer, Date dateStart, Date dateEnd) throws DBException {
        try {
            return (new AccountPaymentDAO(connection).getAccountByIdCustomerSelectDate(idCustomer, dateStart, dateEnd));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }

    }

    /**
     * Change account amount
     *
     * @param idAccount account number
     * @param sum       amount
     * @throws DBException error with database
     */
    @Override
    public void changeBalance(long idAccount, double sum) throws DBException {
        try {
            new AccountPaymentDAO(connection).updateAccountBalance(idAccount, sum);
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Calculation of the amount for all accounts with a negative balance
     *
     * @param idCustomer customer number
     * @return negative account amount
     * @throws DBException error with database
     */
    @Override
    public double getNegativeSum(long idCustomer) throws DBException {
        try {
            double negativeSum = 0;
            List<AccountPayment> acPayments = getAccounts(idCustomer);
            for (AccountPayment aP : acPayments) {
                if (aP.getBalance() < 0) {
                    negativeSum = negativeSum + aP.getBalance();
                }
            }
            return negativeSum;
        } catch (DBException e) {
            log.error(e.toString());
            throw new DBException(e);
        }

    }

    /**
     * Calculation of the amount for all accounts with a positive balance
     *
     * @param idCustomer customer number
     * @return positive account amount
     * @throws DBException error with database
     */
    @Override
    public double getPositiveSum(long idCustomer) throws DBException {
        try {
            double positiveSum = 0;
            List<AccountPayment> acCurrencies = getAccounts(idCustomer);
            for (AccountPayment aP : acCurrencies) {
                if (aP.getBalance() > 0) {
                    positiveSum = positiveSum + aP.getBalance();
                }
            }
            return positiveSum;
        } catch (DBException e) {
            log.error(e.toString());
            throw new DBException(e);
        }

    }

    /**
     * Calculation of the amount for all accounts
     *
     * @param idCustomer customer number
     * @return account amount
     * @throws DBException error with database
     */
    @Override
    public double getSum(long idCustomer) throws DBException {
        try {
            double accountSum = 0.0;
            accountSum = getNegativeSum(idCustomer) + getPositiveSum(idCustomer);
            return accountSum;
        } catch (DBException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Sort customer accounts
     *
     * @param accountPayments list of unsorted accounts
     * @return list of sorted accounts
     */
    @Override
    public List<AccountPayment> sortAccounts(List accountPayments) {
        Collections.sort(accountPayments);
        return accountPayments;
    }

}

