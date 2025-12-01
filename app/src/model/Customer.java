package model;

public class Customer {

    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String status;

    public Customer(int customerID, String firstName, String lastName, String email, String status) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
    }

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

    // DAO compatibility getters:
    public String getCustomerEmail() {
        return email;
    }

    public String getCustomerStatus() {
        return status;
    }

    @Override
    public String toString() {
        return customerID + " - " + firstName + " " + lastName + " (" + email + ")";
    }
}