package services.customerService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import db.DBException;
import db.DBService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import models.client.*;
import dao.customerDao.*;

/**
 * Class describes the behavior of the account payment service
 *
 * @author Baturo Valery
 * @version 1.0
 */
public class ServiceCustomer {

    /**
     * Event Logger for class AccountPaymentService {@value}.
     */
    private static final Logger log = LogManager.getLogger(ServiceCustomer.class.getName());
    /**
     * Connection to database.
     */
    private final Connection connection;

    /**
     * Connection initialization
     */
    public ServiceCustomer() {
        this.connection = new DBService().getMysqlConnection();
    }

    /**
     * Check Is customer in the database
     *
     * @param firstName customer First Name
     * @param lastName  customer Last Name
     * @return true if customer exist and false otherwise
     * @throws DBException database error
     */
    public boolean existCustomer(String firstName, String lastName) throws DBException {
        try {
            return (new CustomersDAO(connection).existCustomer(firstName, lastName));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Returns  customer from database by his name
     *
     * @param firstName customer First Name
     * @param lastName  customer Last Name
     * @return customer with the specified First Name and Last Name or null if not found
     * @throws DBException database error
     */
    public Customer getCustomer(String firstName, String lastName) throws DBException {
        try {

            CustomersDAO cDAO = new CustomersDAO(connection);
            boolean isCustomer = cDAO.existCustomer(firstName, lastName);
            if (isCustomer) {
                return (new CustomersDAO(connection).getObjectByName(firstName, lastName));
            } else System.out.println("Customer not found!");
            return null;
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Add customer to database
     *
     * @param firstName customer First Name
     * @param lastName  customer Last Name
     * @throws DBException database error
     */
    public void insertCustomer(String firstName, String lastName) throws DBException {
        try {
            CustomersDAO cDAO = new CustomersDAO(connection);
            boolean isCustomer = cDAO.existCustomer(firstName, lastName);
            if (!isCustomer) {
                cDAO.insertObject(firstName, lastName);
            } else System.out.println("Customer already is in DataBase!");
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Remove customer from database by his name
     *
     * @param firstName customer First Name
     * @param lastName  customer Last Name
     * @throws DBException database error
     */
    public void deleteCustomer(String firstName, String lastName) throws DBException {
        try {
            CustomersDAO cDAO = new CustomersDAO(connection);
            boolean isCustomer = cDAO.existCustomer(firstName, lastName);
            if (isCustomer) {
                long idCustomer = cDAO.getID(firstName, lastName);
                cDAO.deleteObject(idCustomer);
            } else System.out.println("Customer not found!");
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Returns  customer from database by his id
     *
     * @param id customer number
     * @return customer with the specified id
     * @throws DBException database error
     */
    public Customer getCustomer(long id) throws DBException {
        try {
            return (new CustomersDAO(connection).getObjectById(id));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Returns id  customer's from database by his name
     *
     * @param firstName customer First Name
     * @param lastName  customer Last Name
     * @return id customer's with the specified First Name and Last Name or 0 if not found
     * @throws DBException database error
     */
    public long getCustomerID(String firstName, String lastName) throws DBException {
        try {
            if (existCustomer(firstName, lastName)) {
                return (new CustomersDAO(connection).getID(firstName, lastName));
            } else {
                return 0;
            }
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    /**
     * Get all customers from database
     *
     * @return list of customers or null if there are no users in the database
     * @throws DBException database error
     */
    public List<Customer> getAllCustomers() throws DBException {
        try {
            return (new CustomersDAO(connection).getAllObject());
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }
}
