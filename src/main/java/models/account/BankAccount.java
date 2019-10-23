package models.account;

import org.jetbrains.annotations.*;

/**
 * Abstract class describing a bank account model
 * implements Comparable for comparing accounts
 *
 * @author Baturo Valery
 * @version 1.0
 */

public abstract class BankAccount implements Comparable<BankAccount> {
    /**
     * Account number.
     */
    long idAccount;
    /**
     * Account blocked or unblocked.
     * 0 - accont blocked or 1- account unblocked.
     */
    byte blocked;
    /**
     * Account amount.
     */
    double balance;
    /**
     * Account owner.
     */
    long idCustomer;

    /**
     * Account constructor.
     *
     * @param idAccount   account number.
     * @param blocked     account blocked or unblocked.
     * @param balance     account amount.
     * @param idCustomer  account owner.
     */
    BankAccount(long idAccount, byte blocked, double balance, long idCustomer) {
        this.idAccount = idAccount;
        this.blocked = blocked;
        this.balance = balance;
        this.idCustomer = idCustomer;
    }

    /**
     * Account display on the console.
     */
    public void display() {
    }

    /**
     * Returns id account's
     *
     * @return number account's
     */
    protected long getIdAccount() {
        return this.idAccount;
    }


    /**
     * Returns blocked or unblocked account
     *
     * @return 0 - account unblocked, 1 - account blocked
     */
    protected byte getBlocked() {
        return this.blocked;
    }

    /**
     * Returns amount account
     *
     * @return amount account
     */
    protected double getBalance() {
        return this.balance;
    }

    /**
     * Returns account owner.
     *
     * @return id account owner.
     */
    protected long getIdCustomer() {
        return this.idCustomer;
    }


    /**
     * Comparison of two accounts.
     * Comparison of accounts on their balance.
     *
     * @return -1 - account less compared, 0 - accounts are equal, 1 - account more than compared
     */
    public int compareTo(@NotNull BankAccount o) {
        if (this.balance < o.getBalance())
            return -1;
        else if (o.getBalance() < this.balance)
            return 1;
        return 0;
    }

}
