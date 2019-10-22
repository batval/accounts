package services.customerService;

import dao.customerDao.CustomersDAO;
import models.client.Customer;
import org.junit.Test;
import db.DBService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ServiceCustomerTest {

    private final Connection connection;

    public ServiceCustomerTest() {
        this.connection = new DBService().getMysqlConnection();
    }

    @Test
    public void existCustomerTrue() {

        try {
            boolean actual = new CustomersDAO(connection).existCustomer("test1","test0");
            boolean expected = true;
            assertEquals(expected,actual);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void existCustomerFalse() {
        try {
            boolean actual = new CustomersDAO(connection).existCustomer("test2","test2");
            boolean expected = false;
            assertEquals(expected,actual);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void getCustomer() {
        try {
                Customer customer = new CustomersDAO(connection).getObjectByName("test0","test1");
                assertEquals("test0",customer.getLastName());
                assertEquals("test1",customer.getFirstName());
        }
        catch ( SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void insertCustomer() {
        try {
            CustomersDAO cDAO = new CustomersDAO(connection);
            cDAO.insertObject("firstName","lastName");
            boolean actual = cDAO.existCustomer("firstName","lastName");
            boolean expected=true;
            long idCustomer = cDAO.getID("firstName","lastName");
            cDAO.deleteObject(idCustomer);
            assertEquals(expected,actual);
        }
        catch ( SQLException e){
            System.out.println(e.toString());
        }
        }


    @Test
    public void deleteCustomer() {
        try {
            CustomersDAO cDAO = new CustomersDAO(connection);
            cDAO.insertObject("firstName","lastName");
            long idCustomer = cDAO.getID("firstName","lastName");
            cDAO.deleteObject(idCustomer);
            boolean actual = cDAO.existCustomer("firstName","lastName");
            boolean expected=false;
            assertEquals(expected,actual);
        }
        catch ( SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void testGetCustomer() {
        try {
            long idCustomer=1;
           Customer customer =new  CustomersDAO(connection).getObjectById(idCustomer);
           assertEquals(1,customer.getIdCustomer());
           assertEquals("test0",customer.getLastName());
           assertEquals("test1",customer.getFirstName());
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void getCustomerID() {
        try {
            CustomersDAO cDAO = new CustomersDAO(connection);
           long actual = new CustomersDAO(connection).getID("test1", "test0");
           long expected = 1;
           assertEquals(expected,actual);
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void getAllCustomers() {
        try {
           List<Customer> customers = new  CustomersDAO(connection).getAllObject();

            assertEquals(1,customers.get(0).getIdCustomer());
            assertEquals("test0",customers.get(0).getLastName());
            assertEquals("test1",customers.get(0).getFirstName());

            assertEquals(5,customers.get(1).getIdCustomer());
            assertEquals("petrov",customers.get(1).getLastName());
            assertEquals("petr",customers.get(1).getFirstName());

            assertEquals(9,customers.get(2).getIdCustomer());
            assertEquals("bat",customers.get(2).getLastName());
            assertEquals("val",customers.get(2).getFirstName());


        }
        catch (SQLException e){
            System.out.println(e.toString());
        }
    }
}