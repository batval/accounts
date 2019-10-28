package dao.accountDao;

import models.account.AccountPayment;
import org.jetbrains.annotations.NotNull;
import org.junit.*;
import db.DBService;
import db.executore.Executor;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class AccountPaymentDAOTest {


    private static Executor executor;
    private static AccountPayment accountBlockedOrUnBlocked;
    private static AccountPayment accountPaymentList0;
    private static AccountPayment accountPaymentList1;
    private static AccountPayment accountPaymentList2;
    private static List<AccountPayment> accountPaymentsListById;

    @BeforeClass
    public static void before() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date dateStart = format.parse("2019-10-14");
        Date dateEnd = format.parse("2019-10-17");
        java.sql.Date dateSqlTestStart = new java.sql.Date(dateStart.getTime());
        java.sql.Date dateSqlTestEnd = new java.sql.Date(dateEnd.getTime());
        executor = new Executor(DBService.getMysqlConnection());
        accountBlockedOrUnBlocked = new AccountPayment(7, (byte) 1, -90, 9, dateSqlTestStart);
        accountPaymentList0 = new AccountPayment(7, (byte) 1, -90, 9, dateSqlTestStart);
        accountPaymentList1 = new AccountPayment(8, (byte) 0, 800, 9, dateSqlTestStart);
        accountPaymentList2 = new AccountPayment(9, (byte) 0, -300, 9, dateSqlTestEnd);
        accountPaymentsListById = new ArrayList<AccountPayment>();
        accountPaymentsListById.add(accountPaymentList0);
        accountPaymentsListById.add(accountPaymentList1);
        accountPaymentsListById.add(accountPaymentList2);
    }

    @AfterClass
    public static void after() {
        executor = null;
        accountPaymentsListById.clear();
        accountPaymentList0 = null;
        accountPaymentList1 = null;
        accountPaymentList2 = null;
        accountBlockedOrUnBlocked = null;
    }


    @Before
    public void beforeTest() {
        executor.execUpdate("INSERT INTO accounts.account_payment (idAccountPayment, blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('100', '0', '1', '50', '" + getDateNow() + "')");
    }

    @After
    public void afterTest() {
        executor.execUpdate(("DELETE FROM accounts.account_payment WHERE idAccountPayment='100'"));
    }

    @Test
    public void getAccountById() throws SQLException {
        long idAccount = 9;
        AccountPayment accountPayment = executor.execQuery("SELECT * FROM accounts.account_payment WHERE idAccountPayment='" + idAccount + "'", resultSet -> {
            resultSet.next();
            return new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                    resultSet.getLong(4), resultSet.getDate(5));
        });
        assertEquals(accountPayment, accountPaymentList2);
    }

    private boolean existAccountById(long idAccountPayment) throws SQLException {
        return executor.execQuery("SELECT * FROM accounts.account_payment WHERE idAccountPayment='" + idAccountPayment + "'", resultSet -> {
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

    @NotNull
    private Date getDateNow() {
        Date today = new Date();
        java.sql.Date date = new java.sql.Date(today.getTime());
        return date;
    }

    private boolean checkBlockAccount(long idAccountPayment) throws SQLException {
        return executor.execQuery("SELECT * FROM accounts.account_payment WHERE idAccountPayment='" + idAccountPayment + "' AND blocked='1'", resultSet -> {

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

    @Test
    public void addDeleteAccount() throws SQLException {
        executor.execUpdate(("DELETE FROM accounts.account_payment WHERE idAccountPayment='100'"));
        assertFalse(existAccountById(100));
    }

    @Test
    public void deleteAccountByIdCustomer() throws SQLException {
        executor.execUpdate(("DELETE FROM accounts.account_payment WHERE idCustomer='50'"));
        assertFalse(existAccountById(100));
    }

    @Test
    public void setBlockedAccount() throws SQLException {
        executor.execUpdate("UPDATE accounts.account_payment SET blocked='1' WHERE idAccountPayment='100'");
        assertTrue(checkBlockAccount(100));
    }

    @Test
    public void getAccountBalanceById() throws SQLException {
        double actual = executor.execQuery("SELECT * FROM accounts.account_payment WHERE idAccountPayment='100'", resultSet -> {
            resultSet.next();
            return resultSet.getDouble(3);
        });
        assertEquals(1, actual, 0);
    }

    @Test
    public void getAccountByIdCustomer() throws SQLException {
        List<AccountPayment> accountPayments = new ArrayList<>();
        executor.execQuery("SELECT * FROM accounts.account_payment WHERE idCustomer='9'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });
        assertEquals(accountPaymentsListById, accountPayments);
    }

    @Test
    public void getAccountBlockedOrUnBlocked() throws SQLException {
        List<AccountPayment> accountPayments = new ArrayList<>();
        executor.execQuery("SELECT * FROM accounts.account_payment WHERE idCustomer='9'" + " AND blocked='1'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });
        assertEquals(accountBlockedOrUnBlocked, accountPayments.get(0));
    }

    @Test
    public void getAccountByIdCustomerSelectDate() throws ParseException, SQLException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dStart = format.parse("2019-10-13");
        Date dEnd = format.parse("2019-10-18");
        java.sql.Date dateStart = new java.sql.Date(dStart.getTime());
        java.sql.Date dateEnd = new java.sql.Date(dEnd.getTime());
        List<AccountPayment> accountPayments = new ArrayList<>();
        executor.execQuery("SELECT * FROM accounts.account_payment WHERE idCustomer='9'" + " AND dateOpen>='" + dateStart + "'" + " AND dateOpen<='" + dateEnd + "'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });
        assertEquals(accountPaymentsListById, accountPayments);
    }

    @Test
    public void getAccountMoreThan() throws SQLException {
        double sum = 100;
        List<AccountPayment> accountPayments = new ArrayList<>();
        executor.execQuery("SELECT * FROM accounts.account_payment WHERE idCustomer='9'" + " AND balance>='" + sum + "'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });
        assertEquals(accountPaymentList1, accountPayments.get(0));
    }

    @Test
    public void existAccount() throws SQLException {
        long idCustomer = 9;
        boolean actual = executor.execQuery("SELECT * FROM accounts.account_payment WHERE idCustomer='" + idCustomer + "'", resultSet -> {
            int size = 0;
            if (resultSet != null) {
                resultSet.last();
                size = resultSet.getRow();
            }
            if (size != 0) return true;
            else
                return false;
        });
        assertTrue(actual);
    }

    @Test
    public void existAccountById() throws SQLException {
        long idAccountPayment = 9;
        boolean actual = executor.execQuery("SELECT * FROM accounts.account_payment WHERE  idAccountPayment='" + idAccountPayment + "'", resultSet -> {
            int size = 0;
            if (resultSet != null) {
                resultSet.last();
                size = resultSet.getRow();
            }
            if (size != 0) return true;
            else
                return false;
        });
        assertTrue(actual);
    }

    @Test
    public void checkBlockAccount() throws SQLException {
        long idAccountPayment = 7;
        boolean actual = executor.execQuery("SELECT * FROM accounts.account_payment WHERE idAccountPayment='" + idAccountPayment + "' AND blocked='1'", resultSet -> {
            int size = 0;
            if (resultSet != null) {
                resultSet.last();
                size = resultSet.getRow();
            }
            if (size != 0) return true;
            else
                return false;
        });
        assertTrue(actual);
    }

    @Test
    public void updateAccountBalance() throws SQLException {
        double sum = -9000;
        executor.execUpdate(("UPDATE accounts.account_payment SET balance= balance+'" + sum + "' WHERE idAccountPayment='100'"));
        double actual = executor.execQuery("SELECT * FROM accounts.account_payment WHERE idAccountPayment='100'", resultSet -> {
            resultSet.next();
            return resultSet.getDouble(3);
        });
        assertEquals(-8999, actual, 0);
    }
}