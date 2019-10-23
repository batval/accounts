package dao.customerDao;

import models.client.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import db.executore.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class for working with customers (table customers) in the database
 *
 * @author Baturo Valery
 * @version 1.0
 */
public class CustomersDAO {
    /**
     * Event Logger for class AccountPaymentDAO {@value}.
     */
    private static final Logger log = LogManager.getLogger(CustomersDAO.class.getName());

    /**
     * Object for working with the base.
     */
    private Executor executor;

    /**
     * The connection to database.
     *
     * @param connection - the connection to database.
     */
    @Contract(pure = true)
    public CustomersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    /**
     * Returns customer from database by id
     *
     * @param id customer number
     * @return customer with the specified id or null if not found
     * @throws SQLException database error
     */
    public Customer getObjectById(long id) throws SQLException {
        try {
            return executor.execQuery("select * from accounts.customers where id=" + id, result -> {
                result.next();
                return new Customer(result.getLong(1), result.getString(2), result.getString(3));
            });
        } catch (SQLException e) {
            log.error(e.toString());
            return null;
        }
    }

    /**
     * Returns  customer from database by his name
     *
     * @param firstName customer First Name
     * @param lastName  customer Last Name
     * @return customer with the specified First Name and Last Name or null if not found
     * @throws SQLException database error
     */
    public Customer getObjectByName(String firstName, String lastName) throws SQLException {
        try {
            return executor.execQuery("select * from accounts.customers where firstName='" + firstName + "' AND lastName='" + lastName + "'", result -> {
                result.next();
                return new Customer(result.getLong(1), result.getString(2), result.getString(3));
            });
        } catch (SQLException e) {
            log.error(e.toString());
            return null;
        }
    }

    /**
     * Returns id  customer's from database by his name
     *
     * @param firstName customer First Name
     * @param lastName  customer Last Name
     * @return id customer's with the specified First Name and Last Name or 0 if not found
     * @throws SQLException database error
     */
    public long getID(String firstName, String lastName) throws SQLException {
        try {
            return executor.execQuery("select * from accounts.customers where firstName='" + firstName + "'" + " AND lastName='" + lastName + "'", result -> {
                result.next();
                return result.getLong(1);
            });
        } catch (SQLException e) {
            log.error(e.toString());
            return 0;
        }
    }

    /**
     * Remove customer from database by id
     *
     * @param id customer number
     */
    public void deleteObject(long id) {
        executor.execUpdate("delete from accounts.customers where id='" + id + "'");
    }


    /**
     * Get all customers from database
     *
     * @return list of customers or null if there are no users in the database
     * @throws SQLException database error
     */
    public List<Customer> getAllObject()  throws SQLException {
        try {
            List<Customer> customers = new ArrayList<>();
            executor.execQuery("SELECT * FROM accounts.customers", resultSet -> {
                while (resultSet.next()) {
                    Customer customer = new Customer(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
                    customers.add(customer);
                }
                return customers;
            });
            return customers;
        } catch (SQLException e) {
            log.error(e.toString());
            return null;
        }
    }

    /**
     * Add customer into database
     *
     * @param firstName customer First Name
     * @param lastName  customer Last Name
     */
    public void insertObject(String firstName, String lastName)  {
        executor.execUpdate("insert into accounts.customers (firstName,lastName) values ('" + firstName + "'," + "'" + lastName + "')");
    }

    /**
     * Check Is customer in the database
     *
     * @param firstName customer First Name
     * @param lastName  customer Last Name
     * @return true if customer exist and false otherwise
     * @throws SQLException database error
     */
    public boolean existCustomer(String firstName, String lastName)  throws SQLException  {
        try{
        return executor.execQuery("Select * from accounts.customers Where firstName='" + firstName + "' AND " + "lastName='" + lastName + "'", resultSet -> {
            int size = 0;
            if (resultSet != null) {
                resultSet.last();
                size = resultSet.getRow();
            }

            if (size != 0) return true;
            else
                return false;
        });
        } catch (SQLException e) {
            log.error(e.toString());
            return false;
        }
    }

    /**
     * Check Is customer in the database
     *
     * @param id customer number
     * @return true if customer exist and false otherwise
     * @throws SQLException database error
     */
    public boolean existCustomer(long id) throws SQLException {
        try{
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
        } catch (SQLException e) {
            log.error(e.toString());
            return false;
        }
    }
}
