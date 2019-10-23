package services.accountService;

import db.DBException;

import java.util.Date;
import java.util.List;

/**
 * Interface describes the behavior of the account service
 *
 * @author Baturo Valery
 * @version 1.0
 */

public interface AccountService<T> {

    /**
     * Check if the client has accounts
     *
     * @param idCustomer customer number
     * @return true if account exist and false otherwise
     * @throws DBException database error
     */
    boolean existAccount(long idCustomer) throws DBException;

    /**
     * Check if there is an account
     *
     * @param idAccountPayment account number
     * @return true if account exist and false otherwise
     * @throws DBException database error
     */
    boolean existAccountById(long idAccountPayment) throws DBException;

    /**
     * Check Is account blocked
     *
     * @param idAccountPayment account number
     * @return true if account blocked and false otherwise
     * @throws DBException database error
     */
    boolean checkBlockAccount(long idAccountPayment) throws DBException;

    /**
     * Remove an account from database by id
     *
     * @param idAccount account number
     * @throws DBException database error
     */
    void deleteAccount(long idAccount) throws DBException;

    /**
     * Remove all accounts from database by id customer
     *
     * @param idAccountCustomer customer number
     * @throws DBException database error
     */
    void deleteAllAccountCustomer(long idAccountCustomer) throws DBException;

    /**
     * Block or unblock an account from database by id
     *
     * @param idAccount account number
     * @param blocked   0 or 1. 0 - unblocked account, 1 - blocked account
     * @throws DBException database error
     */
    void setBlockedOrUnblocked(long idAccount, byte blocked) throws DBException;

    /**
     * Returns an account from database by id
     *
     * @param idAccount account number
     * @return account with the specified id or null if not found
     * @throws DBException database error
     */
    T getAccount(long idAccount) throws DBException;

    /**
     * Get all customer accounts by his id
     *
     * @param idCustomer customer number
     * @return list of accounts customer
     * @throws DBException database error
     */
    List<T> getAccounts(long idCustomer) throws DBException;

    /**
     * Get all block or unblock customer accounts by his id
     *
     * @param idCustomer customer number
     * @param blocked    0 or 1. 0 - unblocked account, 1 - blocked account
     * @return list of accounts customer
     * @throws DBException database error
     */
    List<T> getAccountsBlockedOrUnBlocked(long idCustomer, byte blocked) throws DBException;

    /**
     * Get all customer accounts by his id for the period
     *
     * @param idCustomer customer number
     * @param dateStart  beginning of period
     * @param dateEnd    end of period
     * @return list of accounts customer
     * @throws DBException database error
     */
    List<T> getAccountsSelectDate(long idCustomer, Date dateStart, Date dateEnd) throws DBException;

    /**
     * Sort customer accounts
     *
     * @param accountPayments list of unsorted accounts
     * @return list of sorted accounts
     */
    List<T> sortAccounts(List<T> accountPayments);

    /**
     * Change account amount
     *
     * @param idAccount account number
     * @param sum       amount
     * @throws DBException error with database
     */
    void changeBalance(long idAccount, double sum) throws DBException;

    /**
     * Calculation of the amount for all accounts with a negative balance
     *
     * @param idCustomer customer number
     * @return negative account amount
     * @throws DBException error with database
     */
    double getNegativeSum(long idCustomer) throws DBException;

    /**
     * Calculation of the amount for all accounts with a positive balance
     *
     * @param idCustomer customer number
     * @return positive account amount
     * @throws DBException error with database
     */
    double getPositiveSum(long idCustomer) throws DBException;

    /**
     * Calculation of the amount for all accounts
     *
     * @param idCustomer customer number
     * @return account amount
     * @throws DBException error with database
     */
    double getSum(long idCustomer) throws DBException;
}
