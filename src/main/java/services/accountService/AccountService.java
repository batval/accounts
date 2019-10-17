package services.accountService;

import services.dbService.DBException;

import java.util.ArrayList;
import java.util.Date;

public interface AccountService  <T> {

    public boolean existAccount(long idCustomer) throws DBException;

    public boolean existAccountById(long idAccountPayment) throws DBException;

    public boolean checkBlockAccount(long idAccountPayment) throws DBException;

    public void deleteAccount(long idAccount) throws DBException;

    public void setBlockedOrUnblocked(long idAccount, byte blocked) throws DBException;

    public T getAccount(long idAccount) throws DBException;

    public ArrayList<T> getAccounts(long idCustomer) throws DBException;

    public ArrayList<T> getAccountsBlockedOrUnBlocked(long idCustomer, byte blocked) throws DBException;

    public ArrayList<T> getAccountsSelectDate(long idCustomer,Date dateStart, Date dateEnd) throws DBException;

    public void changeBalance (long idAccount, double sum) throws DBException;

    public double getNegativeSum(long idCustomer) throws DBException;

    public double getPositiveSum(long idCustomer) throws DBException;

    public double getSum(long idCustomer) throws DBException;
}
