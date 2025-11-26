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
}