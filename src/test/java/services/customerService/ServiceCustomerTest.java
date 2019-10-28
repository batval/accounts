package services.customerService;

import dao.customerDao.CustomersDAO;
import models.client.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import db.DBService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ServiceCustomerTest {

    private final Connection connection;

    public ServiceCustomerTest() {
        this.connection = new DBService().getMysqlConnection();
    }

    private static Customer customerExpected;
    private static List<Customer> customerList;
    private static final String firstName = "firstName";
    private static final String lastName = "lastName";

    @BeforeClass
    public static void before() {
        customerExpected = new Customer(1, "test1", "test0");
        Customer customerList1 = new Customer(5, "petr", "petrov");
        Customer customerList2 = new Customer(9, "val", "bat");
        customerList = new ArrayList<>();
        customerList.add(customerExpected);
        customerList.add(customerList1);
        customerList.add(customerList2);
    }

    @AfterClass
    public static void after() {
        customerList.clear();
        customerExpected = null;
    }


    @Test
    public void existCustomerTrue() {
        try {
            boolean actual = new CustomersDAO(connection).existCustomer("test1", "test0");
            assertTrue(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void existCustomerFalse() {
        try {
            boolean actual = new CustomersDAO(connection).existCustomer("test2", "test2");
            assertFalse(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void getCustomer() {
        try {
            Customer customerActual = new CustomersDAO(connection).getObjectByName("test1", "test0");
            assertEquals(customerExpected, customerActual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void insertCustomer() {
        try {
            CustomersDAO cDAO = new CustomersDAO(connection);
            cDAO.insertObject(firstName, lastName);
            boolean actual = cDAO.existCustomer(firstName, lastName);
            long idCustomer = cDAO.getID(firstName, lastName);
            cDAO.deleteObject(idCustomer);
            assertTrue(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Test
    public void deleteCustomer() {
        try {
            CustomersDAO cDAO = new CustomersDAO(connection);
            cDAO.insertObject(firstName, lastName);
            long idCustomer = cDAO.getID(firstName, lastName);
            cDAO.deleteObject(idCustomer);
            boolean actual = cDAO.existCustomer(firstName, lastName);
            assertFalse(actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void testGetCustomer() {
        try {
            long idCustomer = 1;
            Customer customerActual = new CustomersDAO(connection).getObjectById(idCustomer);
            assertEquals(customerExpected, customerActual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void getCustomerID() {
        try {
            long actual = new CustomersDAO(connection).getID("test1", "test0");
            long expected = 1;
            assertEquals(expected, actual);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void getAllCustomers() {
        try {
            List<Customer> customers = new CustomersDAO(connection).getAllObject();
            assertEquals(customerList, customers);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}