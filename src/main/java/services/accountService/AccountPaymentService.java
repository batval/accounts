package services.accountService;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import models.account.AccountPayment;
import services.dbService.*;
import dao.accountDao.AccountPaymentDAO;

public class AccountPaymentService implements AccountService {

    private final Connection connection;

    public AccountPaymentService() throws FileNotFoundException {
        this.connection = new DBService().getMysqlConnection();
    }

    //есть ли счет у клиента
    @Override
    public boolean existAccount(long idCustomer) throws DBException{
        try {
            return(new AccountPaymentDAO(connection).existAccount(idCustomer));
        }
        catch (SQLException e){
            throw new DBException(e);
        }
    }

    //есть ли счет по id
    @Override
    public boolean existAccountById(long idAccountPayment) throws DBException{
        try {
            return(new AccountPaymentDAO(connection).existAccountById(idAccountPayment));
        }
        catch (SQLException e){
            throw new DBException(e);
        }
    }

    //блокирован ли счет
    @Override
    public boolean checkBlockAccount(long idAccountPayment) throws DBException{
        try {
            return(new AccountPaymentDAO(connection).checkBlockAccount(idAccountPayment));
        }
        catch (SQLException e){
            throw new DBException(e);
        }
    }

    //добавить счет
    public void addAccountPayment(byte blocked, double balance, long idCustomer,  Date date)throws DBException{
        try {
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            apDAO.addAccount( blocked,  balance,  idCustomer,  date);
        }
        catch (SQLException e){
            throw new DBException(e);
        }
    }


    //удалить счет
    @Override
    public  void  deleteAccount(long idAccount) throws DBException {
        try {
            new AccountPaymentDAO(connection).deleteAccountById(idAccount);
        }
        catch (SQLException e){
            throw new DBException(e);
        }
    }

    //блокировать-разблокировать счет
    @Override
    public void setBlockedOrUnblocked(long idAccount,byte blocked) throws DBException{
        try {
            new AccountPaymentDAO(connection).setBlockedAccount(idAccount,blocked);
        }
        catch (SQLException e){
            throw new DBException(e);
        }
    }

    //найти счет по его номеру
    @Override
    public AccountPayment getAccount(long idAccount) throws DBException{
        try {
            return(new  AccountPaymentDAO(connection).getAccountById(idAccount));
        }
        catch (SQLException e){
            throw new DBException(e);
        }
    }

    //найти все счета клиента
    public ArrayList<AccountPayment> getAccounts(long idCustomer) throws DBException{
        try {
            return(new  AccountPaymentDAO(connection).getAccountByIdCustomer(idCustomer));
        }
        catch (SQLException e){
            throw new DBException(e);
        }
    }

    //найти блокированные/неблокированные счета
    @Override
    public ArrayList<AccountPayment> getAccountsBlockedOrUnBlocked(long idCustomer, byte blocked) throws DBException {
        try {
            return(new  AccountPaymentDAO(connection).getAccountBlockedOrUnBlocked(idCustomer,blocked));
        }
        catch (SQLException e){
            throw new DBException(e);
        }

    }

    //Найти счета клиента открытые за период
    @Override
    public ArrayList<AccountPayment> getAccountsSelectDate(long idCustomer,Date dateStart, Date dateEnd) throws DBException {
        try {
            return(new  AccountPaymentDAO(connection).getAccountByIdCustomerSelectDate(idCustomer,dateStart,dateEnd));
        }
        catch (SQLException e){
            throw new DBException(e);
        }

    }

    //изменить баланс
    @Override
    public void changeBalance (long idAccount, double sum) throws DBException {
        try {
            new  AccountPaymentDAO(connection).updateAccountBalance(idAccount, sum);
        }
        catch (SQLException e){
            throw new DBException(e);
        }
    }

    @Override
    public double getNegativeSum(long idCustomer) throws DBException{
        try {
            double negativeSum=0;
            ArrayList<AccountPayment> acCurrencies = getAccounts(idCustomer);
            for (AccountPayment aP : acCurrencies)
            {
                if (aP.getBalance()<0){
                    negativeSum=negativeSum+ aP.getBalance();
                }
            }
            return negativeSum;
        }
        catch (DBException e){
            throw new DBException(e);
        }

    }

    @Override
    public double getPositiveSum(long idCustomer) throws DBException{
        try {
            double positiveSum=0;
            ArrayList<AccountPayment> acCurrencies = getAccounts(idCustomer);
            for (AccountPayment aP : acCurrencies)
            {
                if (aP.getBalance()>0){
                    positiveSum=positiveSum+ aP.getBalance();
                }
            }
            return positiveSum;
        }
        catch (DBException e){
            throw new DBException(e);
        }

    }

    @Override
    public double getSum(long idCustomer) throws DBException{
        try {
            double accountSum=0;
            accountSum = getNegativeSum(idCustomer)+getPositiveSum(idCustomer);
            return accountSum;
        }
        catch (DBException e){
            throw new DBException(e);
        }
    }


}

