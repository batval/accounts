package dao.accountDao;

import models.account.AccountPayment;
import org.jetbrains.annotations.NotNull;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
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
    private AccountPayment accountPayment;


    @BeforeClass
    public static void before() {
        executor = new Executor(DBService.getMysqlConnection());
    }

    @AfterClass
    public static void after() {
        executor = null;
    }

    @Test
    public void getAccountById() throws SQLException {
        long idAccount = 9;

        accountPayment = executor.execQuery("SELECT * FROM accounts.account_payment WHERE idAccountPayment='" + idAccount + "'", resultSet -> {
            resultSet.next();
            return new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                    resultSet.getLong(4), resultSet.getDate(5));
        });

        assertEquals(idAccount, accountPayment.getIdAccount());
        assertEquals((byte) 0, accountPayment.getBlocked());
        assertEquals(-300, accountPayment.getBalance(), 0);
        assertEquals(9, accountPayment.getIdCustomer());
        assertEquals("2019-10-18", accountPayment.getDate().toString());

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
        executor.execUpdate("INSERT INTO accounts.account_payment (idAccountPayment, blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('100', '0', '1000', '50', '" + getDateNow() + "')");
        assertTrue(existAccountById(100));

        executor.execUpdate(("DELETE FROM accounts.account_payment WHERE idAccountPayment='100'"));
        assertFalse(existAccountById(100));
    }


    @Test
    public void deleteAccountByIdCustomer() throws SQLException {
        executor.execUpdate("INSERT INTO accounts.account_payment (idAccountPayment, blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('100', '0', '1000', '50', '" + getDateNow() + "')");
        assertTrue(existAccountById(100));

        executor.execUpdate(("DELETE FROM accounts.account_payment WHERE idCustomer='50'"));
        assertFalse(existAccountById(100));

    }

    @Test
    public void setBlockedAccount() throws SQLException {

        executor.execUpdate("INSERT INTO accounts.account_payment (idAccountPayment, blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('100', '0', '1000', '50', '" + getDateNow() + "')");

        executor.execUpdate("UPDATE accounts.account_payment SET blocked='1' WHERE idAccountPayment='100'");
        assertTrue(checkBlockAccount(100));

        executor.execUpdate(("DELETE FROM accounts.account_payment WHERE idAccountPayment='100'"));

    }

    @Test
    public void getAccountBalanceById() throws SQLException {
        executor.execUpdate("INSERT INTO accounts.account_payment (idAccountPayment, blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('100', '0', '1000', '50', '" + getDateNow() + "')");
        double actual = executor.execQuery("SELECT * FROM accounts.account_payment WHERE idAccountPayment='100'", resultSet -> {
            resultSet.next();
            return resultSet.getDouble(3);
        });
        assertEquals(1000, actual, 0);
        executor.execUpdate(("DELETE FROM accounts.account_payment WHERE idAccountPayment='100'"));

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
        assertEquals(7, accountPayments.get(0).getIdAccount());
        assertEquals((byte) 1, accountPayments.get(0).getBlocked());
        assertEquals(-90, accountPayments.get(0).getBalance(), 0);
        assertEquals(9, accountPayments.get(0).getIdCustomer());
        assertEquals("2019-10-18", accountPayments.get(0).getDate().toString());
        assertEquals(8, accountPayments.get(1).getIdAccount());
        assertEquals((byte) 0, accountPayments.get(1).getBlocked());
        assertEquals(800, accountPayments.get(1).getBalance(), 0);
        assertEquals(9, accountPayments.get(1).getIdCustomer());
        assertEquals("2019-10-18", accountPayments.get(1).getDate().toString());
        assertEquals(9, accountPayments.get(2).getIdAccount());
        assertEquals((byte) 0, accountPayments.get(2).getBlocked());
        assertEquals(-300, accountPayments.get(2).getBalance(), 0);
        assertEquals(9, accountPayments.get(2).getIdCustomer());
        assertEquals("2019-10-18", accountPayments.get(2).getDate().toString());

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
        assertEquals(7, accountPayments.get(0).getIdAccount());
        assertEquals((byte) 1, accountPayments.get(0).getBlocked());
        assertEquals(-90, accountPayments.get(0).getBalance(), 0);
        assertEquals(9, accountPayments.get(0).getIdCustomer());
        assertEquals("2019-10-18", accountPayments.get(0).getDate().toString());
    }

    @Test
    public void getAccountByIdCustomerSelectDate() throws ParseException, SQLException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dStart = format.parse("2019-10-14");
        Date dEnd = format.parse("2019-10-17");


        java.sql.Date dateStart = new java.sql.Date(dStart.getTime());
        java.sql.Date dateEnd = new java.sql.Date(dEnd.getTime());

        executor.execUpdate("INSERT INTO accounts.account_payment (idAccountPayment, blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('100', '1', '-1000', '50', '" + getDateNow() + "')");
        executor.execUpdate("INSERT INTO accounts.account_payment (idAccountPayment, blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('110', '0', '500', '50', '" + dateStart + "')");
        executor.execUpdate("INSERT INTO accounts.account_payment (idAccountPayment, blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('200', '1', '1', '50', '" + dateEnd + "')");

        List<AccountPayment> accountPayments = new ArrayList<>();
        executor.execQuery("SELECT * FROM accounts.account_payment WHERE idCustomer='50'" + " AND dateOpen>='" + dateStart + "'" + " AND dateOpen<='" + dateEnd + "'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });

        executor.execUpdate(("DELETE FROM accounts.account_payment WHERE idCustomer='50'"));

        assertEquals(110, accountPayments.get(0).getIdAccount());
        assertEquals((byte) 0, accountPayments.get(0).getBlocked());
        assertEquals(500, accountPayments.get(0).getBalance(), 0);
        assertEquals(50, accountPayments.get(0).getIdCustomer());
        assertEquals("2019-10-14", accountPayments.get(0).getDate().toString());
        assertEquals(200, accountPayments.get(1).getIdAccount());
        assertEquals((byte) 1, accountPayments.get(1).getBlocked());
        assertEquals(1, accountPayments.get(1).getBalance(), 0);
        assertEquals(50, accountPayments.get(1).getIdCustomer());
        assertEquals("2019-10-17", accountPayments.get(1).getDate().toString());

    }

    @Test
    public void getAccountMoreThan() throws SQLException {

        double sum = 9000;
        executor.execUpdate("INSERT INTO accounts.account_payment (idAccountPayment, blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('100', '0', '10000', '50', '" + getDateNow() + "')");
        executor.execUpdate("INSERT INTO accounts.account_payment (idAccountPayment, blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('200', '1', '20000', '50', '" + getDateNow() + "')");

        List<AccountPayment> accountPayments = new ArrayList<>();
        executor.execQuery("SELECT * FROM accounts.account_payment WHERE idCustomer='50'" + " AND balance>='" + sum + "'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });

        executor.execUpdate(("DELETE FROM accounts.account_payment WHERE idCustomer='50'"));

        assertEquals(100, accountPayments.get(0).getIdAccount());
        assertEquals((byte) 0, accountPayments.get(0).getBlocked());
        assertEquals(10000, accountPayments.get(0).getBalance(), 0);
        assertEquals(50, accountPayments.get(0).getIdCustomer());
        assertEquals(getDateNow().toString(), accountPayments.get(0).getDate().toString());
        assertEquals(200, accountPayments.get(1).getIdAccount());
        assertEquals((byte) 1, accountPayments.get(1).getBlocked());
        assertEquals(20000, accountPayments.get(1).getBalance(), 0);
        assertEquals(50, accountPayments.get(1).getIdCustomer());
        assertEquals(getDateNow().toString(), accountPayments.get(1).getDate().toString());
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
        executor.execUpdate("INSERT INTO accounts.account_payment (idAccountPayment, blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('100', '0', '10000', '50', '" + getDateNow() + "')");

        executor.execUpdate(("UPDATE accounts.account_payment SET balance= balance+'" + sum + "' WHERE idAccountPayment='100'"));

        double actual = executor.execQuery("SELECT * FROM accounts.account_payment WHERE idAccountPayment='100'", resultSet -> {
            resultSet.next();
            return resultSet.getDouble(3);
        });
        assertEquals(1000, actual, 0);

        executor.execUpdate(("DELETE FROM accounts.account_payment WHERE idAccountPayment='100'"));


    }


}