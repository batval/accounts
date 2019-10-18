package services.accountService;

import services.dbService.DBException;

import java.util.ArrayList;
import java.util.Date;

public interface AccountService  <T> {

     boolean existAccount(long idCustomer) throws DBException;

     boolean existAccountById(long idAccountPayment) throws DBException;

     boolean checkBlockAccount(long idAccountPayment) throws DBException;

     void deleteAccount(long idAccount) throws DBException;

      void  deleteAllAccountCustomer(long idAccountCustomer) throws DBException;

     void setBlockedOrUnblocked(long idAccount, byte blocked) throws DBException;

     T getAccount(long idAccount) throws DBException;

     ArrayList<T> getAccounts(long idCustomer) throws DBException;

     ArrayList<T> getAccountsBlockedOrUnBlocked(long idCustomer, byte blocked) throws DBException;

     ArrayList<T> getAccountsSelectDate(long idCustomer,Date dateStart, Date dateEnd) throws DBException;

     void changeBalance (long idAccount, double sum) throws DBException;

     double getNegativeSum(long idCustomer) throws DBException;

     double getPositiveSum(long idCustomer) throws DBException;

     double getSum(long idCustomer) throws DBException;
}
