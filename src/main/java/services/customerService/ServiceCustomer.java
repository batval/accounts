package services.customerService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.dbService.DBException;
import services.dbService.DBService;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.client.*;
import dao.customerDao.*;

public class ServiceCustomer {

    private static final Logger log = LogManager.getLogger(ServiceCustomer.class.getName());
    private final Connection connection;

    public ServiceCustomer() throws FileNotFoundException {
        this.connection = new DBService().getMysqlConnection();
    }


    public boolean existCustomer(String firstName, String lastName) throws DBException {
        try {
            return(new  CustomersDAO(connection).existCustomer(firstName,lastName));
        }
        catch (SQLException e){
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    public Customer getCustomer(String firstName, String lastName) throws DBException {
        try {

            CustomersDAO cDAO = new CustomersDAO(connection);
            boolean isCustomer = cDAO.existCustomer(firstName,lastName);
            if (isCustomer)
            {
                return (new CustomersDAO(connection).getObjectByName(firstName,lastName));
            }
            else System.out.println("Customer not found!");
            return null;
        }
        catch ( SQLException e){
            log.error(e.toString());
            throw  new DBException(e);
        }
    }


    public void insertCustomer(String firstName, String lastName)throws DBException{
        try {
            CustomersDAO cDAO = new CustomersDAO(connection);
            boolean isCustomer = cDAO.existCustomer(firstName,lastName);
            if (!isCustomer)
            {
                cDAO.insertObject(firstName,lastName);
            }
            else System.out.println("Customer already is in DataBase!");
        }
        catch (SQLException e){
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    public void deleteCustomer(String firstName, String lastName)throws DBException{
        try {
            CustomersDAO cDAO = new CustomersDAO(connection);
            boolean isCustomer = cDAO.existCustomer(firstName,lastName);
            if (isCustomer)
            {
                long idCustomer = cDAO.getID(firstName,lastName);
                cDAO.deleteObject(idCustomer);
            }
            else System.out.println("Customer not found!");
        }
        catch (SQLException e){
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    public Customer getCustomer(long id) throws DBException{
        try {
            return(new  CustomersDAO(connection).getObjectById(id));
        }
        catch (SQLException e){
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    public long getCustomerID(String firstName, String lastName) throws DBException{
        try {
    if (existCustomer(firstName,lastName)){
            return(new  CustomersDAO(connection).getID(firstName,lastName));
        }
    else {
        return 0;
    }   }

        catch (SQLException e){
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    public List<Customer> getAllCustomers() throws DBException{
        try {
            return(new  CustomersDAO(connection).getAllObject());
        }
        catch (SQLException e){
            log.error(e.toString());
            throw new DBException(e);
        }
    }






}
