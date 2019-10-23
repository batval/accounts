package services.accountService;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import db.DBException;
import db.DBService;

import models.account.AccountPayment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.accountDao.AccountPaymentDAO;

public class AccountPaymentService implements AccountService {

    private static final Logger log = LogManager.getLogger(AccountPaymentService.class.getName());
    private final Connection connection;

    public AccountPaymentService() throws FileNotFoundException {
        this.connection = new DBService().getMysqlConnection();
    }

    //есть ли счет у клиента
    @Override
    public boolean existAccount(long idCustomer) throws DBException {
        try {
            return (new AccountPaymentDAO(connection).existAccount(idCustomer));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    //есть ли счет по id
    @Override
    public boolean existAccountById(long idAccountPayment) throws DBException {
        try {
            return (new AccountPaymentDAO(connection).existAccountById(idAccountPayment));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    //блокирован ли счет
    @Override
    public boolean checkBlockAccount(long idAccountPayment) throws DBException {
        try {
            return (new AccountPaymentDAO(connection).checkBlockAccount(idAccountPayment));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    //добавить счет
    public void addAccountPayment(byte blocked, double balance, long idCustomer, Date date) throws DBException {
        try {
            log.info("Add account with parameters: blocked-" + blocked + ", balance-" + balance + ", id customer-" + idCustomer + ", Open date- " + date);
            AccountPaymentDAO apDAO = new AccountPaymentDAO(connection);
            apDAO.addAccount(blocked, balance, idCustomer, date);
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }


    //удалить счет
    @Override
    public void deleteAccount(long idAccount) throws DBException {
        new AccountPaymentDAO(connection).deleteAccountById(idAccount);
    }

    //удалить все счета пользователя счет
    @Override
    public void deleteAllAccountCustomer(long idAccountCustomer) throws DBException {
        if (existAccount(idAccountCustomer)) {
            log.info("Delete accounts for customer with id: " + idAccountCustomer);
            new AccountPaymentDAO(connection).deleteAccountByIdCustomer(idAccountCustomer);
        }
    }


    //блокировать-разблокировать счет
    @Override
    public void setBlockedOrUnblocked(long idAccount, byte blocked) throws DBException {
        try {

            new AccountPaymentDAO(connection).setBlockedAccount(idAccount, blocked);

        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    //найти счет по его номеру
    @Override
    public AccountPayment getAccount(long idAccount) {
        return (new AccountPaymentDAO(connection).getAccountById(idAccount));
    }

    //найти все счета клиента
    public List<AccountPayment> getAccounts(long idCustomer) throws DBException {
        try {
            return (new AccountPaymentDAO(connection).getAccountByIdCustomer(idCustomer));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    //найти блокированные/неблокированные счета
    @Override
    public List<AccountPayment> getAccountsBlockedOrUnBlocked(long idCustomer, byte blocked) throws DBException {
        try {
            return (new AccountPaymentDAO(connection).getAccountBlockedOrUnBlocked(idCustomer, blocked));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }

    }

    //Найти счета клиента открытые за период
    @Override
    public List<AccountPayment> getAccountsSelectDate(long idCustomer, Date dateStart, Date dateEnd) throws DBException {
        try {
            return (new AccountPaymentDAO(connection).getAccountByIdCustomerSelectDate(idCustomer, dateStart, dateEnd));
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }

    }

    //изменить баланс
    @Override
    public void changeBalance(long idAccount, double sum) throws DBException {
        try {
            new AccountPaymentDAO(connection).updateAccountBalance(idAccount, sum);
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    @Override
    public double getNegativeSum(long idCustomer) throws DBException {
        try {
            double negativeSum = 0;
            List<AccountPayment> acPayments = getAccounts(idCustomer);
            for (AccountPayment aP : acPayments) {
                if (aP.getBalance() < 0) {
                    negativeSum = negativeSum + aP.getBalance();
                }
            }
            return negativeSum;
        } catch (DBException e) {
            log.error(e.toString());
            throw new DBException(e);
        }

    }

    @Override
    public double getPositiveSum(long idCustomer) throws DBException {
        try {
            double positiveSum = 0;
            List<AccountPayment> acCurrencies = getAccounts(idCustomer);
            for (AccountPayment aP : acCurrencies) {
                if (aP.getBalance() > 0) {
                    positiveSum = positiveSum + aP.getBalance();
                }
            }
            return positiveSum;
        } catch (DBException e) {
            log.error(e.toString());
            throw new DBException(e);
        }

    }

    @Override
    public double getSum(long idCustomer) throws DBException {
        try {
            double accountSum = 0.0;
            accountSum = getNegativeSum(idCustomer) + getPositiveSum(idCustomer);
            return accountSum;
        } catch (DBException e) {
            log.error(e.toString());
            throw new DBException(e);
        }
    }

    @Override
    public List<AccountPayment> sortAccounts(List accountPayments) {
        Collections.sort(accountPayments);
        return accountPayments;
    }

}

