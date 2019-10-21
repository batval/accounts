package services.accountService;

import services.dbService.DBException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface AccountService  <T> {

     boolean existAccount(long idCustomer) throws DBException;

     boolean existAccountById(long idAccountPayment) throws DBException;

     boolean checkBlockAccount(long idAccountPayment) throws DBException;

     void deleteAccount(long idAccount) throws DBException;

      void  deleteAllAccountCustomer(long idAccountCustomer) throws DBException;

     void setBlockedOrUnblocked(long idAccount, byte blocked) throws DBException;

     T getAccount(long idAccount) throws DBException;

     List<T> getAccounts(long idCustomer) throws DBException;

     List<T> getAccountsBlockedOrUnBlocked(long idCustomer, byte blocked) throws DBException;

     List<T> getAccountsSelectDate(long idCustomer, Date dateStart, Date dateEnd) throws DBException;

     List<T> sortAccounts(List<T> accountPayments);

     void changeBalance (long idAccount, double sum) throws DBException;

     double getNegativeSum(long idCustomer) throws DBException;

     double getPositiveSum(long idCustomer) throws DBException;

     double getSum(long idCustomer) throws DBException;
}
