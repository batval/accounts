package models.account;

import org.jetbrains.annotations.*;

import java.sql.Date;


/**
 * Class describing a model payment account.
 * extends BankAccount
 *
 * @author Baturo Valery
 * @version 1.0
 */

public class AccountPayment extends BankAccount {

    /**
     * Account opening date.
     */
    private Date date;

    /**
     * Account payment constructor.
     *
     * @param idAccount  - account number.
     * @param blocked    - account blocked or unblocked.
     * @param balance    - account amount.
     * @param idCustomer - account owner.
     * @param date       - account opening date
     */
    public AccountPayment(long idAccount, byte blocked, double balance, long idCustomer, Date date) {
        super(idAccount, blocked, balance, idCustomer);
        this.date = date;
    }

    /**
     * Returns id account's
     *
     * @return number account's
     */
    @Override
    public long getIdAccount() {
        return super.idAccount;
    }

    /**
     * Returns blocked or unblocked account
     *
     * @return 0 - account unblocked, 1 - account blocked
     */
    @Override
    public byte getBlocked() {
        return super.blocked;
    }

    /**
     * Returns amount account
     *
     * @return amount account
     */
    @Override
    public double getBalance() {
        return super.balance;
    }

    /**
     * Returns account owner.
     *
     * @return id account owner.
     */
    @Override
    public long getIdCustomer() {
        return super.idCustomer;
    }

    /**
     * Returns opening date.
     *
     * @return account opening date.
     */
    public Date getDate() {
        return this.date;
    }


    /**
     * Account payment display on the console.
     */
    @Override
    public void display() {
        System.out.println("Information: \n" + "Account ID is: " + super.getIdAccount() + "\nType is: Payment account \n" +
                "Blocked: " + super.getBlocked() + "\nBalance: " + super.getBalance() + "\nOpen date: " + this.date + "\n");
    }

    /**
     * Comparison of two accounts payment.
     * Comparison of accounts payment on their balance.
     *
     * @return -1 - account payment less compared, 0 - accounts payment are equal, 1 - account payment more than compared
     */
    public int compareTo(@NotNull BankAccount o) {
        if (this.balance < o.getBalance())
            return -1;
        else if (o.getBalance() < this.balance)
            return 1;
        return 0;
    }

}
