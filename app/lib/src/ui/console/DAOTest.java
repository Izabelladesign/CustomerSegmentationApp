package ui.console;

import dao.CustomerDAO;
import dao.ProductDAO;
import model.Customer;
import model.Product;

import java.util.List;

public class DAOTest {
    public static void main(String[] args) throws Exception {

        CustomerDAO cdao = new CustomerDAO();
        ProductDAO pdao = new ProductDAO();

        System.out.println("=== Customers ===");
        List<Customer> customers = cdao.listAll();
        for (Customer c : customers) {
            System.out.println(c.getFirstName() + " " + c.getLastName());
        }

        System.out.println("\n=== Products ===");
        List<Product> products = pdao.listAll();
        for (Product p : products) {
            System.out.println(p.getProductName() + " — $" + p.getUnitPrice());
        }
    }
}