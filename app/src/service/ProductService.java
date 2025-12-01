package service;

import java.util.List;
import dao.ProductDAO;
import model.Product;

public class ProductService {

    private ProductDAO dao = new ProductDAO();

    public List<Product> getAll() throws Exception {
        return dao.listAll();
    }

    public void addProduct(String name, double price) throws Exception {
        Product p = new Product(0, name, price);
        dao.insert(p);
    }

    public void updateProduct(int id, String name, double price) throws Exception {
        Product p = new Product(id, name, price);
        dao.update(p);
    }

    public void deleteProduct(int id) throws Exception {
        dao.delete(id);
    }
}