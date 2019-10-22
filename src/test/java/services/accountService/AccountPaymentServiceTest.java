package services.accountService;

import dao.accountDao.AccountPaymentDAO;
import models.account.AccountPayment;
import org.junit.Test;
import services.dbService.DBService;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class AccountPaymentServiceTest {

    private final Connection connection;

    public AccountPaymentServiceTest()  {
        this.connection = new DBService().getMysqlConnection();
    }

    @Test
    public void existAccountTrue() {
        try {
            long idCustomer = 9;
           boolean actual = new AccountPaymentDAO(connection).existAccount(idCustomer);
           boolean expected = true;
           assertEquals(expected,actual);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void existAccountFalse() {
        try {
            long idCustomer = -9;
            boolean actual = new AccountPaymentDAO(connection).existAccount(idCustomer);
            boolean expected = false;
            assertEquals(expected,actual);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void existAccountByIdTrue() {
        try {
            long idAccountPayment=8;
           boolean actual= new AccountPaymentDAO(connection).existAccountById(idAccountPayment);
            boolean expected= true;
            assertEquals(expected,actual);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void existAccountByIdFalse() {
        try {
            long idAccountPayment=-8;
            boolean actual= new AccountPaymentDAO(connection).existAccountById(idAccountPayment);
            boolean expected= false;
            assertEquals(expected,actual);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }

    }

    @Test
    public void checkBlockAccountTrue() {
        try {
            long idAccountPayment=7;
            boolean actual= new AccountPaymentDAO(connection).checkBlockAccount(idAccountPayment);
            boolean expected=true;
            assertEquals(expected,actual);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void checkBlockAccountFalse() {
        try {
            long idAccountPayment=8;
            boolean actual= new AccountPaymentDAO(connection).checkBlockAccount(idAccountPayment);
            boolean expected=false;
            assertEquals(expected,actual);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void addAccountPayment() {
        Date today = new Date();
        java.sql.Date date = new java.sql.Date(today.getTime());
        try {
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            apDAO.addAccount((byte)0, 1000, 1000, date);
            boolean actual = new AccountPaymentDAO(connection).existAccount(1000);
            boolean expected = true;
            apDAO.deleteAccountByIdCustomer(1000);
            assertEquals(expected,actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void deleteAccount() {
        Date today = new Date();
        java.sql.Date date = new java.sql.Date(today.getTime());
        try {
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            apDAO.addAccount((byte)0, 1000, 1000, date);
            apDAO.deleteAccountByIdCustomer(1000);
            boolean actual = new AccountPaymentDAO(connection).existAccount(1000);
            boolean expected = false;
            assertEquals(expected,actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void deleteAllAccountCustomer() {
        Date today = new Date();
        java.sql.Date date = new java.sql.Date(today.getTime());
        try {
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            apDAO.addAccount((byte)0, 1000, 1000, date);
            apDAO.addAccount((byte)0, -1000, 1000, date);
            apDAO.addAccount((byte)0, 1, 1000, date);
            apDAO.deleteAccountByIdCustomer(1000);
            boolean actual = new AccountPaymentDAO(connection).existAccount(1000);
            boolean expected = false;
            assertEquals(expected,actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    @Test
    public void setBlockedOrUnblockedTrue() {
        try {
            long idAccount=9;
            byte blocked=1;
            new AccountPaymentDAO(connection).setBlockedAccount(idAccount,blocked);
            boolean actual =  new AccountPaymentDAO(connection).checkBlockAccount(idAccount);
            boolean expected = true;
            assertEquals(expected,actual);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void setBlockedOrUnblockedFalse() {
        try {
            long idAccount=9;
            byte blocked=0;
            new AccountPaymentDAO(connection).setBlockedAccount(idAccount,blocked);
            boolean actual =  new AccountPaymentDAO(connection).checkBlockAccount(idAccount);
            boolean expected = false;
            assertEquals(expected,actual);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void getAccount() {

        AccountPayment actual= new AccountPaymentDAO(connection).getAccountById(7);
        assertEquals(7,actual.getIdAccount());
        assertEquals(1,actual.getBlocked());
        assertEquals(-90,actual.getBalance(),0);
        assertEquals(9,actual.getIdCustomer());
        assertEquals("2019-10-18",actual.getDate().toString());

    }

    @Test
    public void getAccounts() {
        try {
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer=1000;
            apDAO.addAccount((byte)0, 1000, 1000, date);
            apDAO.addAccount((byte)0, -1000, 1000, date);
            apDAO.addAccount((byte)0, 1, 1000, date);
            List<AccountPayment> paymentList= new  AccountPaymentDAO(connection).getAccountByIdCustomer(idCustomer);

            assertEquals(0,paymentList.get(0).getBlocked());
            assertEquals(1000,paymentList.get(0).getBalance(),0);
            assertEquals(1000,paymentList.get(0).getIdCustomer());
            assertEquals(date.toString(),paymentList.get(0).getDate().toString());
            assertEquals(0,paymentList.get(1).getBlocked());
            assertEquals(-1000,paymentList.get(1).getBalance(),0);
            assertEquals(1000,paymentList.get(1).getIdCustomer());
            assertEquals(date.toString(),paymentList.get(1).getDate().toString());
            assertEquals(0,paymentList.get(2).getBlocked());
            assertEquals(1,paymentList.get(2).getBalance(),0);
            assertEquals(1000,paymentList.get(2).getIdCustomer());
            assertEquals(date.toString(),paymentList.get(2).getDate().toString());

            apDAO.deleteAccountByIdCustomer(1000);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void getAccountsBlockedOrUnBlocked() {
        try {
            byte blocked=1;
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer=1000;
            apDAO.addAccount((byte)1, 1000, idCustomer, date);
            apDAO.addAccount((byte)1, -1000, idCustomer, date);
            apDAO.addAccount((byte)0, 1, idCustomer, date);
            List<AccountPayment> paymentList= apDAO.getAccountBlockedOrUnBlocked(idCustomer,blocked);
            assertEquals(1,paymentList.get(0).getBlocked());
            assertEquals(1000,paymentList.get(0).getBalance(),0);
            assertEquals(1000,paymentList.get(0).getIdCustomer());
            assertEquals(date.toString(),paymentList.get(0).getDate().toString());
            assertEquals(1,paymentList.get(1).getBlocked());
            assertEquals(-1000,paymentList.get(1).getBalance(),0);
            assertEquals(1000,paymentList.get(1).getIdCustomer());
            assertEquals(date.toString(),paymentList.get(1).getDate().toString());

            apDAO.deleteAccountByIdCustomer(idCustomer);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void getAccountsSelectDate() {

        try {
            Date today = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dStart=format.parse("2019-10-14");
            Date dEnd = format.parse("2019-10-20");

            java.sql.Date date = new java.sql.Date(today.getTime());
            java.sql.Date dateStart = new java.sql.Date(dStart.getTime());
            java.sql.Date dateEnd = new java.sql.Date(dEnd.getTime());

            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer=1000;
            apDAO.addAccount((byte)1, 1000, idCustomer, date);
            apDAO.addAccount((byte)1, -1000, idCustomer, dateStart);
            apDAO.addAccount((byte)0, 1, idCustomer, dateEnd);
            List<AccountPayment> paymentList= apDAO.getAccountByIdCustomerSelectDate(idCustomer,dateStart,dateEnd);
            assertEquals(1,paymentList.get(0).getBlocked());
            assertEquals(-1000,paymentList.get(0).getBalance(),0);
            assertEquals(1000,paymentList.get(0).getIdCustomer());
            assertEquals("2019-10-14",paymentList.get(0).getDate().toString());
            assertEquals(0,paymentList.get(1).getBlocked());
            assertEquals(1,paymentList.get(1).getBalance(),0);
            assertEquals(1000,paymentList.get(1).getIdCustomer());
            assertEquals("2019-10-20",paymentList.get(1).getDate().toString());


            apDAO.deleteAccountByIdCustomer(idCustomer);
        }
        catch (SQLException | ParseException e){
            System.out.println(e.toString());
        }

    }

    @Test
    public void changeBalance() {
        try {
            long idAccount=0;
            double sum=90;
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            apDAO.deleteAccountByIdCustomer(1000);
            apDAO.addAccount((byte)1, 1000, 1000, date);
            double expected = 1090;
            double actual=0;
            List <AccountPayment> paymentList = apDAO.getAccountByIdCustomer(1000);
            for (AccountPayment accountPayment:paymentList) {
                apDAO.updateAccountBalance(accountPayment.getIdAccount(), sum);
                idAccount=accountPayment.getIdAccount();
            }
            actual= apDAO.getAccountBalanceById(idAccount);
            apDAO.deleteAccountByIdCustomer(1000);
            assertEquals(expected,actual,0);

        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void getNegativeSum() {

        try {
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer=1000;
            apDAO.addAccount((byte)0, 1000, 1000, date);
            apDAO.addAccount((byte)0, -1000, 1000, date);
            apDAO.addAccount((byte)0, 1, 1000, date);
            apDAO.addAccount((byte)0, -400, 1000, date);

            double expected = -1400;
            double negativeSum=0;
            List<AccountPayment> acPayments =apDAO.getAccountByIdCustomer(idCustomer);
            for (AccountPayment aP : acPayments)
            {
                if (aP.getBalance()<0){
                    negativeSum=negativeSum+ aP.getBalance();
                }
            }

            apDAO.deleteAccountByIdCustomer(1000);
            assertEquals(expected,negativeSum,0);

        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void getPositiveSum() {
        try {
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer=1000;
            apDAO.addAccount((byte)0, 1000, 1000, date);
            apDAO.addAccount((byte)0, -1000, 1000, date);
            apDAO.addAccount((byte)0, 1, 1000, date);
            apDAO.addAccount((byte)0, -400, 1000, date);

            double expected = 1001;
            double positiveSum=0;
            List<AccountPayment> acPayments =apDAO.getAccountByIdCustomer(idCustomer);
            for (AccountPayment aP : acPayments)
            {
                if (aP.getBalance()>0){
                    positiveSum=positiveSum+ aP.getBalance();
                }
            }

            apDAO.deleteAccountByIdCustomer(1000);
            assertEquals(expected,positiveSum,0);

        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void getSum() {
        try {
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer=1000;
            apDAO.addAccount((byte)0, 1000, 1000, date);
            apDAO.addAccount((byte)0, -1000, 1000, date);
            apDAO.addAccount((byte)0, 1, 1000, date);
            apDAO.addAccount((byte)0, -400, 1000, date);

            double expected = -399;
            double accountSum=0;

            List<AccountPayment> acPayments =apDAO.getAccountByIdCustomer(idCustomer);
            for (AccountPayment aP : acPayments)
            {
                accountSum=accountSum+ aP.getBalance();
            }

            apDAO.deleteAccountByIdCustomer(1000);
            assertEquals(expected,accountSum,0);

        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void sortAccounts() {
        try {
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer=1000;
            apDAO.addAccount((byte)0, 1000, 1000, date);
            apDAO.addAccount((byte)1, -1000, 1000, date);
            apDAO.addAccount((byte)0, 1, 1000, date);
            apDAO.addAccount((byte)1, -400, 1000, date);
            List<AccountPayment> acPayments =apDAO.getAccountByIdCustomer(idCustomer);
            Collections.sort(acPayments);
            apDAO.deleteAccountByIdCustomer(1000);
            assertEquals(1,acPayments.get(0).getBlocked());
            assertEquals(-1000,acPayments.get(0).getBalance(),0);
            assertEquals(1000,acPayments.get(0).getIdCustomer());
            assertEquals(date.toString(),acPayments.get(0).getDate().toString());
            assertEquals(1,acPayments.get(1).getBlocked());
            assertEquals(-400,acPayments.get(1).getBalance(),0);
            assertEquals(1000,acPayments.get(1).getIdCustomer());
            assertEquals(date.toString(),acPayments.get(1).getDate().toString());
            assertEquals(0,acPayments.get(2).getBlocked());
            assertEquals(1,acPayments.get(2).getBalance(),0);
            assertEquals(1000,acPayments.get(2).getIdCustomer());
            assertEquals(date.toString(),acPayments.get(2).getDate().toString());
            assertEquals(0,acPayments.get(3).getBlocked());
            assertEquals(1000,acPayments.get(3).getBalance(),0);
            assertEquals(1000,acPayments.get(3).getIdCustomer());
            assertEquals(date.toString(),acPayments.get(3).getDate().toString());
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
        }
}