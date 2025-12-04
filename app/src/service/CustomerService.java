package service;

import dao.CustomerDAO;
import model.Customer;
import java.util.List;

public class CustomerService {

    private CustomerDAO dao = new CustomerDAO();

    public List<Customer> getAll() throws Exception {
        return dao.listAll();
    }

    public void addCustomer(String f, String l, String email, String status) throws Exception {
        Customer c = new Customer(0, f, l, email, status);
        dao.insert(c);
    }

    public void updateCustomer(int id, String f, String l, String email, String status) throws Exception {
        Customer c = new Customer(id, f, l, email, status);
        dao.update(c);
    }

    public void deleteCustomer(int id) throws Exception {
        dao.delete(id);
    }

    public List<Customer> getBySegment(int segmentID) throws Exception {
        return dao.listBySegment(segmentID);
    }
}