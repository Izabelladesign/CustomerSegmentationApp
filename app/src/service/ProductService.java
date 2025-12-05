package service;

import java.util.List;
import dao.ProductDAO;
import model.Product;

public class ProductService {
// Uses ProductDAO to handle database operations for products
    private ProductDAO dao = new ProductDAO();
 // Returns a list of all products
    public List<Product> getAll() throws Exception {
        return dao.listAll();
    }
 // Adds a product with no inventory specified (default 0)
    public void addProduct(String name, double price) throws Exception {
        addProduct(name, price, 0);
    }
 // Adds a new product with name, price, and inventory
    public void addProduct(String name, double price, int inventory) throws Exception {
        Product p = new Product(0, name, price, inventory);
        dao.insert(p);
    }
 // Updates a product without changing inventory
    public void updateProduct(int id, String name, double price) throws Exception {
        updateProduct(id, name, price, 0);
    }
// Updates a product’s details, including inventory if needed
    public void updateProduct(int id, String name, double price, int inventory) throws Exception {
        Product p = new Product(id, name, price, inventory);
        dao.update(p);
    }
 // Deletes a product by ID
    public void deleteProduct(int id) throws Exception {
        dao.delete(id);
    }
}
