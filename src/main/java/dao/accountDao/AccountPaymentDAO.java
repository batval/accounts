package dao.accountDao;


import models.account.AccountPayment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.dbService.executore.Executor;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class AccountPaymentDAO extends AbstractJdbcDao <AccountPayment> {

    private static final Logger log = LogManager.getLogger(AccountPaymentDAO.class.getName());
    private Executor executor;

    public AccountPaymentDAO(Connection connection) {
        this.executor = new Executor(connection);
    }


    @Override
    public String getSelectQuery (){ return "SELECT * FROM accounts.account_payment ";
    }


    @Override
    public String getUpdateQuery (){ return "UPDATE accounts.account_payment SET ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO accounts.account_payment ";
    }


    @Override
    public String getDeleteQuery() {
        return "DELETE FROM accounts.account_payment WHERE idAccountPayment='";
    }

    @Override
    public String getDeleteQueryCustomer() {
        return "DELETE FROM accounts.account_payment WHERE idCustomer='";
    }


    //Search models.models.account by ID models.models.account
    @Override
    public AccountPayment getAccountById(long id)  {
       try {
           return executor.execQuery(getSelectQuery() + "WHERE idAccountPayment='" + id + "'", resultSet -> {
               resultSet.next();
               return new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                       resultSet.getLong(4), resultSet.getDate(5));
           });
       }
       catch (SQLException e)
       {
           log.error(e.toString());
           return null;
       }
    }

    //Delete models.models.account
    @Override
    public void deleteAccountById(long id)  {
        executor.execUpdate((getDeleteQuery() + id + "'"));
    }

    @Override
    public void deleteAccountByIdCustomer(long idCustomer)  {
        executor.execUpdate((getDeleteQueryCustomer()+idCustomer+"'"));
    }


    //Set blocked accounts or unblocked
    @Override
    public void setBlockedAccount(long id,byte blocked) throws SQLException {
        executor.execUpdate(getUpdateQuery()+"blocked='"+blocked+"' WHERE idAccountPayment='" + id + "'");
    }

    //Get balance by  ID models.models.account
    @Override
    public double getAccountBalanceById(long id) throws SQLException {
        return executor.execQuery(getSelectQuery()+"WHERE idAccountPayment='" + id + "'", resultSet -> {
            resultSet.next();
            return  resultSet.getDouble(3);
        });
    }

    //Search all accounts
    @Override
    public ArrayList<AccountPayment> getAccountByIdCustomer(long idCustomer) throws SQLException {
        ArrayList<AccountPayment>  accountPayments = new ArrayList<>();
        //   System.out.println(getSelectQuery()+ "WHERE idCustomer='" + idCustomer + "'");
        executor.execQuery(getSelectQuery()+ "WHERE idCustomer='" + idCustomer + "'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment  accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });
        return accountPayments;
    }

    //search blocked accounts
    @Override
    public ArrayList<AccountPayment> getAccountBlockedOrUnBlocked(long idCustomer, byte blocked) throws SQLException {
        ArrayList<AccountPayment>  accountPayments = new ArrayList<>();

        executor.execQuery(getSelectQuery()+" WHERE idCustomer='" + idCustomer + "'"+" AND blocked='"+blocked+"'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment  accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });
        return accountPayments;
    }

    //Times lapse search
    @Override
    public ArrayList<AccountPayment> getAccountByIdCustomerSelectDate(long idCustomer, Date dateStart, Date dateEnd) throws SQLException {
        ArrayList<AccountPayment>  accountPayments = new ArrayList<>();
        executor.execQuery(getSelectQuery()+ "WHERE idCustomer='" + idCustomer + "'"+" AND date>='"+dateStart+"'"+ " AND date<='"+dateEnd+"'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment  accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });
        return accountPayments;
    }

    //Find more balance
    @Override
    public ArrayList<AccountPayment> getAccountMoreThan(long idCustomer, double sum) throws SQLException {
        ArrayList<AccountPayment>  accountPayments = new ArrayList<>();
        executor.execQuery(getSelectQuery()+"WHERE idCustomer='" + idCustomer + "'"+" AND balance>='"+sum+"'", resultSet -> {
            while (resultSet.next()) {
                AccountPayment  accountPayment = new AccountPayment(resultSet.getLong(1), resultSet.getByte(2), resultSet.getDouble(3),
                        resultSet.getLong(4), resultSet.getDate(5));
                accountPayments.add(accountPayment);
            }
            return accountPayments;
        });
        return accountPayments;
    }


    //Check models.models.account existence
    @Override
    public boolean existAccount(long idCustomer) throws SQLException{
        return   executor.execQuery(getSelectQuery()+ "WHERE idCustomer='" +idCustomer+"'",resultSet -> {
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

    //Check  existence by idAccounts
    @Override
    public boolean existAccountById(long idAccountPayment) throws SQLException{
        return   executor.execQuery(getSelectQuery()+ "WHERE idAccountPayment='" +idAccountPayment+"'",resultSet -> {
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

    //Check models.models.account existence
    @Override
    public boolean checkBlockAccount(long idAccountPayment) throws SQLException{
        return   executor.execQuery(getSelectQuery()+ "WHERE idAccountPayment='" +idAccountPayment+"' AND blocked='1'",resultSet -> {

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

    //Update balance
    @Override
    public void updateAccountBalance(long idAccountPayment, double  sum) throws SQLException {
        executor.execUpdate((getUpdateQuery()+"balance= balance+'" + sum + "' WHERE idAccountPayment='"+idAccountPayment+"'"));
    }

    //Add Account

    public void addAccount(byte blocked, double balance, long idCustomer,  Date date) throws SQLException {
        executor.execUpdate(getCreateQuery()+"(blocked, balance,idCustomer, dateOpen) " +
                "VALUES ('"+blocked+"', '"+balance+"', '"+idCustomer+"', '" +date+"')");
    }





}


