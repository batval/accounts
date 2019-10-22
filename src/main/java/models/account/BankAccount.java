package models.account;

import org.jetbrains.annotations.*;

public abstract class BankAccount implements Comparable<BankAccount> {
    protected long idAccount;
    protected byte blocked;
    protected double balance;
    protected  long idCustomer;

    public BankAccount(long idAccount,byte blocked,double balance, long idCustomer){
        this.idAccount = idAccount;
        this.blocked = blocked;
        this.balance = balance;
        this.idCustomer = idCustomer;
    }

    public void display (){
    }

    protected  long getIdAccount(){
        return idAccount;
    }

    protected byte getBlocked(){
        return blocked;
    }

    protected double getBalance (){
        return balance;
    }

    protected long getIdCustomer(){
        return idCustomer;
    }


    public int compareTo(@NotNull BankAccount o) {
        if(this.balance<o.getBalance())
            return -1;
        else if(o.getBalance()<this.balance)
            return 1;
        return 0;
    }

}
