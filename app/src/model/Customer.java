package model;

/**
 * Represents a customer in the system such as ID, name, email, and status.
 */

public class Customer {

    //variables 
    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String status;

    /**
     * Constructor to create a new Customer object with all attributes.
     *
     * @param customerID Unique ID of the customer
     * @param firstName Customer's first name
     * @param lastName Customer's last name
     * @param email Customer's email address
     * @param status Customer's current status
     */
    public Customer(int customerID, String firstName, String lastName, String email, String status) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
    }

    //getters 
    public int getCustomerID() { 
        return customerID; 
    }

    public String getFirstName() { 
        return firstName; 
    }

    public String getLastName() { 
        return lastName; 
    }

    public String getEmail() { 
        return email; 
    }

    public String getStatus() { 
        return status; 
    }

    public String getCustomerEmail() {
        return email;
    }

    public String getCustomerStatus() {
        return status;
    }

    //returns a string representation of the customer 
    @Override
    public String toString() {
        return customerID + " - " + firstName + " " + lastName + " (" + email + ")";
    }
}
