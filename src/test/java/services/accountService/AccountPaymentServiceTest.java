package services.accountService;

import dao.accountDao.AccountPaymentDAO;
import models.account.AccountPayment;
import org.junit.*;
import db.DBService;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class AccountPaymentServiceTest {

    private final Connection connection;

    public AccountPaymentServiceTest() {
        this.connection = new DBService().getMysqlConnection();
    }

    private static AccountPayment accountBlockedOrUnBlocked;
    private static AccountPayment accountPaymentList0;
    private static AccountPayment accountPaymentList1;
    private static AccountPayment accountPaymentList2;
    private static AccountPayment accountPaymentList3;
    private static List<AccountPayment> accountPaymentsListById;
    private static List<AccountPayment> accountPaymentsListSort;

    @BeforeClass
    public static void before() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date dateStart = format.parse("2019-10-14");
        Date dateEnd = format.parse("2019-10-17");
        java.sql.Date dateSqlTestStart = new java.sql.Date(dateStart.getTime());
        java.sql.Date dateSqlTestEnd = new java.sql.Date(dateEnd.getTime());
        accountBlockedOrUnBlocked = new AccountPayment(7, (byte) 1, -90, 9, dateSqlTestStart);
        accountPaymentList0 = new AccountPayment(7, (byte) 1, -90, 9, dateSqlTestStart);
        accountPaymentList1 = new AccountPayment(8, (byte) 0, 800, 9, dateSqlTestStart);
        accountPaymentList2 = new AccountPayment(9, (byte) 1, -300, 9, dateSqlTestEnd);
        accountPaymentList3 = new AccountPayment(9, (byte) 0, -300, 9, dateSqlTestEnd);
        accountPaymentsListById = new ArrayList<AccountPayment>();
        accountPaymentsListById.add(accountPaymentList0);
        accountPaymentsListById.add(accountPaymentList1);
        accountPaymentsListById.add(accountPaymentList2);
        accountPaymentsListSort= new ArrayList<AccountPayment>();
        accountPaymentsListSort.add(accountPaymentList3);
        accountPaymentsListSort.add(accountPaymentList0);
        accountPaymentsListSort.add(accountPaymentList1);
    }

    @AfterClass
    public static void after() {
        accountPaymentsListById.clear();
        accountPaymentList0 = null;
        accountPaymentList1 = null;
        accountPaymentList2 = null;
        accountBlockedOrUnBlocked = null;
    }



    @Before
    public void beforeTest()  {
       try {
           Date today = new Date();
           java.sql.Date date = new java.sql.Date(today.getTime());
           AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
           apDAO.addAccount((byte) 0, 1000, 1000, date);
           apDAO.addAccount((byte) 0, -1000, 1000, date);
           apDAO.addAccount((byte) 0, 1, 1000, date);
           apDAO.addAccount((byte) 0, -400, 1000, date);
       }
       catch (SQLException e){
           System.out.println(e.toString());
       }
    }

    @After
    public void afterTest() {
        AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
        apDAO.deleteAccountByIdCustomer(1000);
    }
    @Test
    public void existAccountTrue() {
        try {
            long idCustomer = 9;
            boolean actual = new AccountPaymentDAO(connection).existAccount(idCustomer);
            assertTrue(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void existAccountFalse() {
        try {
            long idCustomer = -9;
            boolean actual = new AccountPaymentDAO(connection).existAccount(idCustomer);
            assertFalse(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void existAccountByIdTrue() {
        try {
            long idAccountPayment = 8;
            boolean actual = new AccountPaymentDAO(connection).existAccountById(idAccountPayment);
            assertTrue(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void existAccountByIdFalse() {
        try {
            long idAccountPayment = -8;
            boolean actual = new AccountPaymentDAO(connection).existAccountById(idAccountPayment);
            assertFalse(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void checkBlockAccountTrue() {
        try {
            long idAccountPayment = 7;
            boolean actual = new AccountPaymentDAO(connection).checkBlockAccount(idAccountPayment);
            assertTrue(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void checkBlockAccountFalse() {
        try {
            long idAccountPayment = 8;
            boolean actual = new AccountPaymentDAO(connection).checkBlockAccount(idAccountPayment);
            assertFalse(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void addAccountPayment() {
        try {
            boolean actual = new AccountPaymentDAO(connection).existAccount(1000);
            assertTrue(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void deleteAccount() {
        try {
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
           apDAO.deleteAccountByIdCustomer(1000);
            boolean actual = new AccountPaymentDAO(connection).existAccount(1000);
            assertFalse(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void deleteAllAccountCustomer() {
        try {
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            apDAO.deleteAccountByIdCustomer(1000);
            boolean actual = new AccountPaymentDAO(connection).existAccount(1000);
            boolean expected = false;
            assertEquals(expected, actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    @Test
    public void setBlockedOrUnblockedTrue() {
        try {
            long idAccount = 9;
            byte blocked = 1;
            new AccountPaymentDAO(connection).setBlockedAccount(idAccount, blocked);
            boolean actual = new AccountPaymentDAO(connection).checkBlockAccount(idAccount);
            assertTrue(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void setBlockedOrUnblockedFalse() {
        try {
            long idAccount = 9;
            byte blocked = 0;
            new AccountPaymentDAO(connection).setBlockedAccount(idAccount, blocked);
            boolean actual = new AccountPaymentDAO(connection).checkBlockAccount(idAccount);
            assertFalse(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void getAccount() {
        AccountPayment actual = new AccountPaymentDAO(connection).getAccountById(7);
       assertEquals(accountPaymentList0,actual);
    }

    @Test
    public void getAccounts() {
        try {
            long idCustomer = 9;
            List<AccountPayment> paymentList = new AccountPaymentDAO(connection).getAccountByIdCustomer(idCustomer);
            assertEquals(accountPaymentsListById,paymentList);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void getAccountsBlockedOrUnBlocked() {
        try {
            byte blocked = 1;
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer = 9;
            List<AccountPayment> paymentList = apDAO.getAccountBlockedOrUnBlocked(idCustomer, blocked);
            assertEquals(accountBlockedOrUnBlocked, paymentList.get(0));
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void getAccountsSelectDate() {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dStart = format.parse("2019-10-14");
            Date dEnd = format.parse("2019-10-20");
            java.sql.Date dateStart = new java.sql.Date(dStart.getTime());
            java.sql.Date dateEnd = new java.sql.Date(dEnd.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer = 9;
            List<AccountPayment> paymentList = apDAO.getAccountByIdCustomerSelectDate(idCustomer, dateStart, dateEnd);
            assertEquals(accountPaymentsListById, paymentList);
        } catch (SQLException | ParseException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void changeBalance() {
        try {
            long idAccount = 0;
            double sum = 90;
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            apDAO.deleteAccountByIdCustomer(1000);
            apDAO.addAccount((byte) 1, 1000, 1000, date);
            double expected = 1090;
            double actual = 0;
            List<AccountPayment> paymentList = apDAO.getAccountByIdCustomer(1000);
            for (AccountPayment accountPayment : paymentList) {
                apDAO.updateAccountBalance(accountPayment.getIdAccount(), sum);
                idAccount = accountPayment.getIdAccount();
            }
            actual = apDAO.getAccountBalanceById(idAccount);
            apDAO.deleteAccountByIdCustomer(1000);
            assertEquals(expected, actual, 0);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void getNegativeSum() {

        try {
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer = 1000;
            apDAO.addAccount((byte) 0, 1000, 1000, date);
            apDAO.addAccount((byte) 0, -1000, 1000, date);
            apDAO.addAccount((byte) 0, 1, 1000, date);
            apDAO.addAccount((byte) 0, -400, 1000, date);

            double expected = -2800;
            double negativeSum = 0;
            List<AccountPayment> acPayments = apDAO.getAccountByIdCustomer(idCustomer);
            for (AccountPayment aP : acPayments) {
                if (aP.getBalance() < 0) {
                    negativeSum = negativeSum + aP.getBalance();
                }
            }

            apDAO.deleteAccountByIdCustomer(1000);
            assertEquals(expected, negativeSum, 0);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void getPositiveSum() {
        try {
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer = 1000;
            apDAO.addAccount((byte) 0, 1000, 1000, date);
            apDAO.addAccount((byte) 0, -1000, 1000, date);
            apDAO.addAccount((byte) 0, 1, 1000, date);
            apDAO.addAccount((byte) 0, -400, 1000, date);

            double expected = 2002;
            double positiveSum = 0;
            List<AccountPayment> acPayments = apDAO.getAccountByIdCustomer(idCustomer);
            for (AccountPayment aP : acPayments) {
                if (aP.getBalance() > 0) {
                    positiveSum = positiveSum + aP.getBalance();
                }
            }

            apDAO.deleteAccountByIdCustomer(1000);
            assertEquals(expected, positiveSum, 0);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void getSum() {
        try {
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer = 1000;
            apDAO.addAccount((byte) 0, 1000, 1000, date);
            apDAO.addAccount((byte) 0, -1000, 1000, date);
            apDAO.addAccount((byte) 0, 1, 1000, date);
            apDAO.addAccount((byte) 0, -400, 1000, date);

            double expected = -798;
            double accountSum = 0;

            List<AccountPayment> acPayments = apDAO.getAccountByIdCustomer(idCustomer);
            for (AccountPayment aP : acPayments) {
                accountSum = accountSum + aP.getBalance();
            }

            apDAO.deleteAccountByIdCustomer(1000);
            assertEquals(expected, accountSum, 0);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void sortAccounts() {
        try {
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            long idCustomer = 9;
            List<AccountPayment> acPayments = apDAO.getAccountByIdCustomer(idCustomer);
            Collections.sort(acPayments);
            assertEquals(accountPaymentsListSort,acPayments);
            //assertEquals(1, acPayments.get(0).getBlocked());
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}