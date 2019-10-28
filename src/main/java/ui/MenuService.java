package ui;

import models.account.AccountPayment;
import models.client.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.accountService.AccountPaymentService;
import services.customerService.ServiceCustomer;
import db.DBException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class for working with console menu
 *
 * @author Baturo Valery
 * @version 1.0
 */

public class MenuService {
    /**
     * Event Logger for class MenuService
     */
    private static final Logger log = LogManager.getLogger(MenuService.class.getName());
    /**
     * Service for working with AccountPaymentService
     */
    private AccountPaymentService apService = new AccountPaymentService();
    /**
     * Service for working with ServiceCustomer
     */
    private ServiceCustomer customerService = new ServiceCustomer();
    /**
     * Reading of entered values
     */
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    /**
     * Validation of entered values
     */
    private InputCheck inputCheck = new InputCheck();

    public MenuService() throws FileNotFoundException {
    }

    /**
     * Reading of entered values
     *
     * @return string array with first name and last name of customer
     */
    private String[] readCustomer() {
        String[] customerName = new String[2];
        try {
            System.out.println("Enter last name:");
            customerName[0] = reader.readLine();
            System.out.println("Enter first name");
            customerName[1] = reader.readLine();
        } catch (IOException e) {
            log.error(e.toString());
        }
        return customerName;
    }

    /**
     * Returns  customer from database by his name
     *
     * @return customer with the specified First Name and Last Name or null if not found
     */
    private Customer readCustomerObject() {
        String[] customerName = readCustomer();
        Customer customer = null;
        try {
            customer = customerService.getCustomer(customerName[1], customerName[0]);
        } catch (DBException e) {
            log.error(e.toString());
        }
        return customer;
    }

    /**
     * Display  accounts
     *
     * @param apList list of accounts
     */
    private void displayAccounts(List<AccountPayment> apList) {
        for (AccountPayment apPayment : apList) {
            apPayment.display();
        }
    }

    /**
     * Add customer menu selection
     */
    public void addCustomerMenu() {
        try {
            String[] customerName = readCustomer();
            log.info("Add customer: " + customerName[0] + " " + customerName[1]);
            customerService.insertCustomer(customerName[1], customerName[0]);
        } catch (DBException e) {
            log.error("Error adding customer. \n" + e.toString());
        }
    }

    /**
     * Delete customer menu selection
     */
    public void deleteCustomerMenu() {
        try {
            String[] customerName = readCustomer();
            apService.deleteAllAccountCustomer(customerService.getCustomerID(customerName[1], customerName[0]));
            log.info("Delete customer: " + customerName[0] + " " + customerName[1]);
            customerService.deleteCustomer(customerName[1], customerName[0]);
        } catch (DBException e) {
            log.error(e.toString());
        }
    }

    /**
     * Show accounts of customer menu selection
     */
    public void showAccountsCustomerMenu() {

        Customer customer = readCustomerObject();
        try {
            if (customer != null) {
                long idCustomer = customer.getIdCustomer();
                apService.getAccounts(customer.getIdCustomer());
                List<AccountPayment> apList = new ArrayList<>(apService.getAccounts(idCustomer));
                if (apList.size() == 0) {
                    log.info("Customer's (" + customer.getLastName() + " " + customer.getFirstName() + ") accounts not found!");
                } else {
                    for (AccountPayment apPayment : apList) {
                        apPayment.display();
                    }
                }
            }
        } catch (DBException e) {
            log.error(e.toString());
        }
    }

    /**
     * Add account menu selection
     */
    public void addAccountMenu() {
        Customer customer = readCustomerObject();
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
                    log.info("Add account for " + customer.getLastName() + " " + customer.getFirstName() + " with balance " + balance);
                    apService.addAccountPayment((byte) 0, balance, idCustomer, date);
                } else {
                    System.out.println("Customer not found!");

                }
            } else {
                System.out.println("Incorrect data");
            }
        } catch (DBException | IOException e) {
            log.error(e.toString());
        }
    }

    /**
     * Delete account menu selection
     */
    public void deleteAccountMenu() {
        Customer customer = readCustomerObject();
        try {

            if (customer != null) {
                long idCustomer = customer.getIdCustomer();
                apService.getAccounts(customer.getIdCustomer());
                List<AccountPayment> apList = new ArrayList<>(apService.getAccounts(idCustomer));
                if (apList.size() == 0) {
                    System.out.println("Customer's (" + customer.getLastName() + " " + customer.getFirstName() + ") accounts not found!");
                } else {
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
                        } else {
                            System.out.println("Account with id:" + idAccount + " not found");
                        }
                    }
                }
            }
        } catch (IOException | DBException e) {
            log.error(e.toString());
        }
    }

    /**
     * Block or unblock account menu selection
     */
    public void blockOrUnblockedAccountMenu() {
        Customer customer = readCustomerObject();
        try {
            if (customer != null) {
                long idCustomer = customer.getIdCustomer();
                apService.getAccounts(customer.getIdCustomer());
                List<AccountPayment> apList = new ArrayList<>(apService.getAccounts(idCustomer));
                if (apList.size() == 0) {
                    System.out.println("Customer's (" + customer.getLastName() + " " + customer.getFirstName() + ") accounts not found!");
                } else {
                    displayAccounts(apList);
                    System.out.println("Enter id account for block:");
                    String line = reader.readLine();
                    if (inputCheck.isInt(line)) {
                        int idAccount = Integer.parseInt(line);
                        if (apService.existAccountById(idAccount)) {
                            if (apService.checkBlockAccount(idAccount) == false) {
                                System.out.println("Blocked account with id:" + idAccount);
                                apService.setBlockedOrUnblocked(idAccount, (byte) 1);
                            } else {
                                System.out.println("Unblocked account with id:" + idAccount);
                                apService.setBlockedOrUnblocked(idAccount, (byte) 0);
                            }
                        } else {
                            System.out.println("Account with id " + idAccount + " not found!");
                        }
                    }
                }
            }
        } catch (IOException | DBException e) {
            log.error(e.toString());
        }
    }

    /**
     * Change balance of account's menu selection
     */
    public void changeBalanceMenu() {
        Customer customer = readCustomerObject();
        try {
            if (customer != null) {
                long idCustomer = customer.getIdCustomer();
                apService.getAccounts(customer.getIdCustomer());
                List<AccountPayment> apList = new ArrayList<>(apService.getAccounts(idCustomer));
                if (apList.size() == 0) {
                    System.out.println("Customer's (" + customer.getLastName() + " " + customer.getFirstName() + ") accounts not found!");
                } else {
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
                        } else {
                            System.out.println("Account with id " + idAccount + " not found!");
                        }
                    }
                }
            }
        } catch (IOException | DBException e) {
            log.error(e.toString());
        }
    }

    /**
     * Show accounts statistics
     */
    public void showStat() {
        Customer customer = readCustomerObject();
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
                }
            }

        } catch (DBException e) {
            log.error(e.toString());
        }
    }


}
