package service;

import dao.CustomerDAO;
import model.Customer;
import java.util.List;

public class CustomerService {
// Uses the CustomerDAO to handle all database operations
    private CustomerDAO dao = new CustomerDAO();
// Returns all customers in the system
    public List<Customer> getAll() throws Exception {
        return dao.listAll();
    }
// Adds a new customer by creating a Customer object and passing it to the DAO
    public void addCustomer(String f, String l, String email, String status) throws Exception {
        Customer c = new Customer(0, f, l, email, status);
        dao.insert(c);
    }
 // Updates an existing customer based on the given ID
    public void updateCustomer(int id, String f, String l, String email, String status) throws Exception {
        Customer c = new Customer(id, f, l, email, status);
        dao.update(c);
    }
// Deletes a customer by ID
    public void deleteCustomer(int id) throws Exception {
        dao.delete(id);
    }
// Returns a list of customers that belong to a specific segment
    public List<Customer> getBySegment(int segmentID) throws Exception {
        return dao.listBySegment(segmentID);
    }
}
