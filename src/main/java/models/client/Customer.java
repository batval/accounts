package models.client;

public class Customer implements Comparable<Customer> {

    private final long idCustomer;
    private final String firstName;
    private final String lastName;

    public  Customer(long idCustomer, String firstName, String lastName){
        this.idCustomer = idCustomer;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getIdCustomer(){
        return this.idCustomer;
    }

    public String getFirstName (){
        return this.firstName;
    }

    public String getLastName () {
        return this.lastName;
    }

    public  void   printInfo(){
        System.out.println(this.firstName +" "+this.lastName);
    }

    @Override
    public int compareTo(Customer o) {
        return ((this.lastName+this.firstName).compareTo(o.getLastName()+o.getFirstName()));
    }
}
