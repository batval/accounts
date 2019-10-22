package models.client;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {

  private static Customer customer;
  private static Customer compareCustomer;

    @BeforeClass
    public static void before() {
        customer = new Customer(1000,"testFirstName","testLastName");
        compareCustomer= new Customer(1001,"compareFirstName","compareLastName");

    }

    @AfterClass
    public static void after() {
         customer = null;
         compareCustomer=null;
    }

    @Test
    public void getIdCustomer() {
        long expected=1000;
        long actual = customer.getIdCustomer();
        assertEquals(expected,actual);
    }

    @Test
    public void getFirstName() {
      String expected="testFirstName";
      String actual = customer.getFirstName();
      assertEquals(expected,actual);
    }

    @Test
    public void getLastName() {
      String expected="testLastName";
      String actual = customer.getLastName();
      assertEquals(expected,actual);
    }

    @Test
    public void printInfo() {
      String expected = "testFirstName testLastName";
      String actual =customer.getFirstName()+" "+ customer.getLastName();
      assertEquals(expected,actual);
    }

    @Test
    public void compareTo() {
      int expected=0;
      int actual = (customer.getLastName()+customer.getFirstName()).compareTo(compareCustomer.getLastName()+compareCustomer.getFirstName());
      assertTrue(expected<actual);

    }
}