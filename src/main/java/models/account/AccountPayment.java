package models.account;

import org.jetbrains.annotations.*;

import java.sql.Date;


public class AccountPayment  extends BankAccount {

    private Date date;

    public AccountPayment(long idAccount, byte blocked, double balance, long idCustomer,  Date date){
        super(idAccount,blocked,balance,idCustomer);
        this.date = date;
    }

    @Override
    public long getIdAccount(){
        return super.idAccount;
    }

    @Override
    public byte getBlocked(){
        return super.blocked;
    }

    @Override
    public double getBalance (){
        return super.balance;
    }

    @Override
    public long getIdCustomer(){
        return super.idCustomer;
    }

    public Date getDate (){
        return this.date;
    }



    @Override
    public void display (){
        System.out.println("Information: \n"+ "Account ID is: "+ super.getIdAccount()+"\nType is: Payment account \n"+
                "Blocked: "+super.getBlocked()+"\nBalance: "+ super.getBalance() + "\nOpen date: "+this.date+ "\n");
    }


    public int compareTo(@NotNull BankAccount o) {
        if(this.balance<o.getBalance())
            return -1;
        else if(o.getBalance()<this.balance)
            return 1;
        return 0;
    }

}
