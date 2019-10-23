package models.account;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class AccountPaymentTest {

    private static AccountPayment accountPayment;
    private static AccountPayment accountPaymentCompare;
    private static AccountPayment accountPaymentCompareEquals;

    @BeforeClass
    public static void before() {
        Date today = new Date();
        java.sql.Date date = new java.sql.Date(today.getTime());
        accountPayment = new AccountPayment(100, (byte) 1, -1000, 200, date);
        accountPaymentCompare = new AccountPayment(111, (byte) 0, 2000, 300, date);
        accountPaymentCompareEquals = new AccountPayment(120, (byte) 0, 2000, 300, date);
    }

    @AfterClass
    public static void after() {
        accountPayment = null;
        accountPaymentCompare = null;
        accountPaymentCompareEquals = null;
    }

    @Test
    public void getIdAccount() {
        long expected = 100;
        long actual = accountPayment.getIdAccount();
        assertEquals(expected, actual);
    }

    @Test
    public void getBlocked() {
        byte expected = 1;
        byte actual = accountPayment.getBlocked();
        assertEquals(expected, actual);
    }

    @Test
    public void getBalance() {
        double expected = -1000;
        double actual = accountPayment.getBalance();
        assertEquals(expected, actual, 0);
    }

    @Test
    public void getIdCustomer() {
        long expected = 200;
        long actual = accountPayment.getIdCustomer();
        assertEquals(expected, actual);
    }

    @Test
    public void getDate() {
        Date today = new Date();
        java.sql.Date date = new java.sql.Date(today.getTime());
        String expected = date.toString();
        String actual = accountPayment.getDate().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void display() {
        String expected = "Information: \n" + "Account ID is: " + 100 + "\nType is: Payment account \n" +
                "Blocked: " + 1 + "\nBalance: " + -1000.0 + "\nOpen date: " + "2019-10-22" + "\n";
        String actual = "Information: \n" + "Account ID is: " + accountPayment.getIdAccount() + "\nType is: Payment account \n" +
                "Blocked: " + accountPayment.getBlocked() + "\nBalance: " + accountPayment.getBalance() + "\nOpen date: " + "2019-10-22" + "\n";
        assertEquals(expected, actual);
    }

    @Test
    public void testEqual() {
        int result = accountPaymentCompare.compareTo(accountPaymentCompareEquals);
        assertTrue("expected to be equal", result == 0);
    }

    @Test
    public void testGreaterThan() {
        int result = accountPaymentCompare.compareTo(accountPayment);
        assertTrue("expected to be greater than", result >= 1);
    }

    @Test
    public void testLessThan() {
        int result = accountPayment.compareTo(accountPaymentCompare);
        assertTrue("expected to be less than", result <= -1);
    }
}