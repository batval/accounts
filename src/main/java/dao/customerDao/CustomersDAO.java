package dao.customerDao;

import models.client.Customer;
import org.jetbrains.annotations.Contract;
import services.dbService.executore.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersDAO {

    private Executor executor;

    @Contract(pure = true)
    public CustomersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }


    public Customer getObjectById(long id) throws SQLException {
        return executor.execQuery("select * from accounts.customers where id=" + id, result -> {
            result.next();
            return new Customer(result.getLong(1), result.getString(2),result.getString(3));
        });
    }

    public Customer getObjectByName(String firstName, String lastName) throws SQLException {
        return executor.execQuery("select * from accounts.customers where firstName='" + firstName+"' AND lastName='"+ lastName+"'", result -> {
            result.next();
            return new Customer(result.getLong(1), result.getString(2),result.getString(3));
        });
    }

    public long getID(String firstName, String lastName) throws SQLException {
        return executor.execQuery("select * from accounts.customers where firstName='" + firstName + "'"+" AND lastName='"+lastName+"'", result -> {
            result.next();
            return result.getLong(1);
        });
    }


    public void deleteObject(long id) throws SQLException {
        executor.execUpdate("delete from accounts.customers where id='" + id + "'");
    }


    //Find all Customer

    public List<Customer> getAllObject() throws SQLException {
        List<Customer> customers = new ArrayList<Customer>();
        executor.execQuery("SELECT * FROM accounts.customers", resultSet -> {
            while (resultSet.next()) {
                Customer  customer = new Customer(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
                customers.add(customer);
            }
            return customers;
        });
        return customers;
    }


    public void insertObject(String firstName,String lastName) throws SQLException {
        executor.execUpdate("insert into accounts.customers (firstName,lastName) values ('" + firstName + "',"+ "'"+lastName+"')");
    }

    public boolean existCustomer(String firstName, String lastName) throws SQLException{
        return   executor.execQuery("Select * from accounts.customers Where firstName='" +firstName+"' AND "+"lastName='"+lastName+"'",resultSet -> {
            int size =0;
            if (resultSet != null)
            {
                resultSet.last();
                size = resultSet.getRow();
            }

            if (size !=0) return true;
            else
                return  false;
        });
    }

    public boolean existCustomer(long id) throws SQLException{
        return   executor.execQuery("Select * from accounts.customers Where id='" +id+"'",resultSet -> {
            int size =0;
            if (resultSet != null)
            {
                resultSet.last();
                size = resultSet.getRow();
            }

            if (size !=0) return true;
            else
                return  false;
        });
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table customers");
    }

}
