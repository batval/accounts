package models.client;


/**
 * Class describing a model customer.
 * implements Comparable for customer sorting
 *
 * @author Baturo Valery
 * @version 1.0
 */
public class Customer implements Comparable<Customer> {

    /**
     * Customer number.
     */
    private final long idCustomer;
    /**
     * Customer first name.
     */
    private final String firstName;
    /**
     * Customer last name.
     */
    private final String lastName;

    /**
     * Customer constructor.
     *
     * @param idCustomer  - customer number.
     * @param firstName    -customer first name.
     * @param lastName    - customer last name.
     */
    public  Customer(long idCustomer, String firstName, String lastName){
        this.idCustomer = idCustomer;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Returns id customer's
     *
     * @return number customer's
     */
    public long getIdCustomer(){
        return this.idCustomer;
    }

    /**
     * Returns fist name customer's
     *
     * @return fist name customer's
     */
    public String getFirstName (){
        return this.firstName;
    }
    /**
     * Returns last name customer's
     *
     * @return last name customer's
     */
    public String getLastName () {
        return this.lastName;
    }

    /**
     * Display customer name
     *
     */
    public  void   printInfo(){
        System.out.println(this.firstName +" "+this.lastName);
    }

    /**
     * Comparison of two customers.
     * Comparison of customers by their names.
     *
     * @return -1 - customer less compared, 0 - customers are equal, 1 - customer more than compared
     */
    @Override
    public int compareTo(Customer o) {
        return ((this.lastName+this.firstName).compareTo(o.getLastName()+o.getFirstName()));
    }
}
