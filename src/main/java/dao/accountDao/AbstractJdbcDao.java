package dao.accountDao;


import java.sql.SQLException;
import java.util.*;

public abstract class AbstractJdbcDao<T> {
    /**
     * Возвращает sql запрос для получения всех записей.
     * <p/>
     * SELECT * FROM [Table]
     */
    public abstract String getSelectQuery();

    /**
     * Возвращает sql запрос для вставки новой записи в базу данных.
     * <p/>
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     */
    public abstract String getCreateQuery();

    /**
     * Возвращает sql запрос для удаления записи из базы данных.
     * <p/>
     * DELETE FROM [Table] WHERE id= ?;
     */
    public abstract String getDeleteQuery();

    public abstract String getUpdateQuery ();

    public abstract T getAccountById(long id) throws SQLException;

    public abstract void deleteAccountById(long id) throws SQLException;

    public abstract String  getDeleteQueryCustomer() throws  SQLException;

    public abstract void deleteAccountByIdCustomer(long idCustomer) throws SQLException;

    public abstract  void setBlockedAccount(long id,byte blocked) throws SQLException;

    public abstract  double getAccountBalanceById(long id) throws SQLException;

    public abstract ArrayList<T> getAccountByIdCustomer(long idCustomer) throws SQLException;

    public abstract ArrayList<T> getAccountBlockedOrUnBlocked(long idCustomer, byte blocked) throws SQLException;

    public abstract ArrayList<T> getAccountByIdCustomerSelectDate(long idCustomer, Date dateStart, Date dateEnd) throws SQLException;

    public abstract ArrayList<T> getAccountMoreThan(long idCustomer, double sum) throws SQLException;

    public abstract boolean existAccount(long idCustomer) throws SQLException;

    public abstract boolean existAccountById(long idAccountPayment) throws SQLException;

    public abstract boolean checkBlockAccount(long idAccountPayment) throws SQLException;

    public abstract void updateAccountBalance(long idAccountPayment, double  sum) throws SQLException;

}
