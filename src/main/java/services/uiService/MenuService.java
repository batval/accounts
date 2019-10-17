package services.uiService;

import models.account.AccountPayment;
import models.client.Customer;
import services.accountService.AccountPaymentService;
import services.customerService.ServiceCustomer;
import services.dbService.DBException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuService {

    private AccountPaymentService apService = new AccountPaymentService();
    private InputCheck inputCheck = new InputCheck();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private ServiceCustomer customerService = new ServiceCustomer();

    public MenuService() throws FileNotFoundException {
    }

    public Customer readCustomer () {
        String [] customerName = new String[2];
        Customer customer = null;
        try {
            System.out.println("Enter last name:");
            customerName[0] = reader.readLine();
            System.out.println("Enter first name");
            customerName[1] = reader.readLine();
            customer = customerService.getCustomer(customerName[1],customerName[0]);
        }
        catch (IOException | DBException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public void displayAccounts(List<AccountPayment> apList){
        for (AccountPayment apPayment : apList) {
            apPayment.display();
        }
    }

    public void addCustomerMenu (){
         try {
          Customer customer = readCustomer();
            System.out.println("Add customer: "+ customer.getLastName() + " "+customer.getFirstName());
             customerService.insertCustomer(customer.getFirstName(),customer.getLastName());
        } catch ( DBException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomerMenu() {
        try {
            Customer customer = readCustomer();
            apService.deleteAccount(customerService.getCustomerID(customer.getFirstName(),customer.getLastName()));
            customerService.deleteCustomer(customer.getFirstName(),customer.getLastName());
            System.out.println("Delete customer: "+ customer.getLastName() + " "+customer.getFirstName());
        } catch ( DBException e) {
            e.printStackTrace();
        }
    }

    public void showAccountsCustomerMenu(){
        Customer customer = readCustomer();
        try {
            if (customer!=null) {
                long idCustomer = customer.getIdCustomer();
                apService.getAccounts(customer.getIdCustomer());
                List<AccountPayment> apList = new ArrayList<>(apService.getAccounts(idCustomer));
                if (apList.size()==0)
                {
                    System.out.println("Customer's ("+customer.getLastName()+" "+customer.getFirstName()+") accounts not found!");
                }
                else  {      for (AccountPayment apPayment : apList) {
                    apPayment.display();
                }
                }}
        } catch ( DBException e) {
            e.printStackTrace();
        }
    }

    public void addAccountMenu() {
        Customer customer = readCustomer();
        try {
            Date today = new Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            double balance = 0;
            long idCustomer = 0;
            System.out.println("Enter balance");
            String line = reader.readLine();
            if (inputCheck.isDouble(line)) {
                balance = Double.parseDouble(line);
                if (customerService.existCustomer(customer.getFirstName(), customer.getLastName())) {
                    idCustomer = customerService.getCustomerID(customer.getFirstName(), customer.getLastName());
                    System.out.println("Add account for " + customer.getLastName() + " " + customer.getFirstName() + " with balance " + balance);
                    apService.addAccountPayment((byte) 0, balance, idCustomer, date);
                } else {
                    System.out.println("Customer not found!");
                }
            } else {
                System.out.println("Incorrect data");
            }
        }
        catch ( DBException | IOException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteAccountMenu() {
        Customer customer = readCustomer();
        try {

            if (customer!=null) {
                long idCustomer = customer.getIdCustomer();
                apService.getAccounts(customer.getIdCustomer());
                List<AccountPayment> apList = new ArrayList<>(apService.getAccounts(idCustomer));
                if (apList.size()==0)
                {
                    System.out.println("Customer's ("+customer.getLastName()+" "+customer.getFirstName()+") accounts not found!");
                }
                else
                    {
                    for (AccountPayment apPayment : apList) {
                        apPayment.display();
                    }
                    System.out.println("Enter id account for delete:");
                    String line = reader.readLine();
                    if (inputCheck.isInt(line)) {
                        int idAccount = Integer.parseInt(line);
                        if (apService.existAccountById(idAccount)) {
                            System.out.println("Delete account with id:" + idAccount);
                            apService.deleteAccount(idAccount);
                        } else { System.out.println("Account with id:" + idAccount+" not found");}
                    }}}
        } catch (IOException | DBException e) {
            e.printStackTrace();
        }
    }

    public void blockOrUnblockedAccountMenu(){
        Customer customer = readCustomer();
        try {
            if (customer!=null) {
                long idCustomer = customer.getIdCustomer();
                apService.getAccounts(customer.getIdCustomer());
                List<AccountPayment> apList = new ArrayList<>(apService.getAccounts(idCustomer));
                if (apList.size()==0)
                {
                    System.out.println("Customer's ("+customer.getLastName()+" "+customer.getFirstName()+") accounts not found!");
                }
                else  {    displayAccounts(apList);
                    System.out.println("Enter id account for block:");
                    String line = reader.readLine();
                    if (inputCheck.isInt(line))
                    {  int idAccount = Integer.parseInt(line);
                     if (apService.existAccountById(idAccount)) {
                            if (apService.checkBlockAccount(idAccount) == false) {
                                System.out.println("Blocked account with id:" + idAccount);
                                apService.setBlockedOrUnblocked(idAccount, (byte) 1);
                            } else {
                                System.out.println("Unblocked account with id:" + idAccount);
                                apService.setBlockedOrUnblocked(idAccount, (byte) 0);
                            }
                        }
                        else {System.out.println("Account with id "+ idAccount+" not found!");}
                    }}}
        } catch (IOException | DBException e) {
            e.printStackTrace();
        }
    }

    public void changeBalanceMenu(){
        Customer customer = readCustomer();
        try {
            if (customer!=null) {
                long idCustomer = customer.getIdCustomer();
                apService.getAccounts(customer.getIdCustomer());
                List<AccountPayment> apList = new ArrayList<>(apService.getAccounts(idCustomer));
                if (apList.size()==0)
                {
                    System.out.println("Customer's ("+customer.getLastName()+" "+customer.getFirstName()+") accounts not found!");
                }
                else  {
                    displayAccounts(apList);
                    System.out.println("Enter id account for change balance:");
                    String line = reader.readLine();
                    if (inputCheck.isInt(line)) {
                        int idAccount = Integer.parseInt(line);
                        if (apService.existAccountById(idAccount)) {
                            System.out.println("Enter sum: ");
                            String lineSum = reader.readLine();
                            if (inputCheck.isDouble(lineSum)) {
                                double sum = Double.parseDouble(lineSum);
                                System.out.println("The balance is changed by the amount: " + sum);
                                apService.changeBalance(idAccount, sum);
                            }
                        }
                        else {System.out.println("Account with id "+ idAccount+" not found!");}
                    }
                }}
        } catch (IOException | DBException e) {
            e.printStackTrace();
        }
    }

    public void showStat(){
        Customer customer = readCustomer();
        try {
            if (customer != null) {
                long idCustomer = customer.getIdCustomer();
                apService.getAccounts(customer.getIdCustomer());
                List<AccountPayment> apList = new ArrayList<>(apService.getAccounts(idCustomer));
                if (apList.size() == 0) {
                    System.out.println("Customer's (" + customer.getLastName() + " " + customer.getFirstName() + ") accounts not found!");
                } else {
                    System.out.println("Information:");
                    System.out.println("Total negative balance: " + apService.getNegativeSum(idCustomer));
                    System.out.println("Total positive balance: " + apService.getPositiveSum(idCustomer));
                    System.out.println("Total balance: " + apService.getSum(idCustomer));
                }}

        } catch(DBException e){
            e.printStackTrace();
        }
    }


}
