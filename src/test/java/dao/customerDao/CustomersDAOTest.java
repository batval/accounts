package dao.customerDao;

import models.client.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import db.DBService;
import db.executore.Executor;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CustomersDAOTest {


    private static Executor executor;
    private static Customer customer;
    private static List<Customer> customerList;
    private  static final String firstName = "test1";
    private static final String lastName = "test0";

    @BeforeClass
    public static void before() {
        executor = new Executor(DBService.getMysqlConnection());
        customer = new Customer(1,"test1","test0");
        Customer  customerList1 = new Customer(5,"petr","petrov");
        Customer  customerList2 = new Customer(9,"val","bat");
        customerList = new ArrayList<>();
        customerList.add(customer);
        customerList.add(customerList1);
        customerList.add(customerList2);
    }

    @AfterClass
    public static void after() {
        executor = null;
    }

    @Test
    public void getObjectById() throws SQLException {
        long id = 1;
       Customer customerBd = executor.execQuery("select * from accounts.customers where id='" + id + "'", result -> {
            result.next();
            return new Customer(result.getLong(1), result.getString(2), result.getString(3));
        });
        assertEquals(customer, customerBd);
    }

    @Test
    public void getObjectByName() throws SQLException {
       Customer customerBd = executor.execQuery("select * from accounts.customers where firstName='" + firstName + "' AND lastName='" + lastName + "'", result -> {
            result.next();
            return new Customer(result.getLong(1), result.getString(2), result.getString(3));
        });
        assertEquals(customer, customerBd);
    }

    @Test
    public void getID() throws SQLException {
        long expected = 1;
        long actual = executor.execQuery("select * from accounts.customers where firstName='" + firstName + "'" + " AND lastName='" + lastName + "'", result -> {
            result.next();
            return result.getLong(1);
        });
        assertEquals(expected, actual);
    }

    private boolean isExist(long id) throws SQLException {
        return executor.execQuery("Select * from accounts.customers Where id='" + id + "'", resultSet -> {
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
    public void deleteInsertObject() throws SQLException {
        long id = 1;
        boolean actual = isExist(1);
        assertTrue(actual);
        executor.execUpdate("delete from accounts.customers where id='" + id + "'");
        actual = isExist(id);
        executor.execUpdate("insert into accounts.customers (id,firstName,lastName) values ('1', 'test1', 'test0')");
        assertFalse(actual);
    }

    @Test
    public void getAllObject() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        executor.execQuery("SELECT * FROM accounts.customers", resultSet -> {
            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
                customers.add(customer);
            }
            return customers;
        });
        assertEquals(customerList,customers);
    }


    @Test
    public void existCustomer() throws SQLException {
        long id = 1;

        boolean actual = executor.execQuery("Select * from accounts.customers Where id='" + id + "'", resultSet -> {
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
}