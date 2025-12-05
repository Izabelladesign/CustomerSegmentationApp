package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class ProductDAO {
    /**
     * Returns all products from the database.
     * Used to load the full product list in the GUI.
     */
    public List<Product> listAll() throws Exception {
        String sql = "SELECT ProductID, ProductName, ProductPrice, Inventory FROM Products";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Product> list = new ArrayList<>();

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("ProductPrice"),
                        rs.getInt("Inventory")
                );
                list.add(p);
            }
            return list;
        }
    }
    /**
     * Looks up a single product by its ID.
     * Returns null if the product doesn't exist.
     */
    public Product findById(int productID) throws Exception {
        String sql = "SELECT * FROM Products WHERE ProductID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("ProductID"),
                            rs.getString("ProductName"),
                            rs.getDouble("ProductPrice"),
                            rs.getInt("Inventory")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * Adds a new product to the database.
     */
    public void insert(Product p) throws Exception {
        String sql = "INSERT INTO Products (ProductName, ProductPrice, Inventory) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getProductName());
            stmt.setDouble(2, p.getUnitPrice());
            stmt.setInt(3, p.getInventory());
            stmt.executeUpdate();
        }
    }
    /**
     * Updates an existing product's details.
     */
    public void update(Product p) throws Exception {
        String sql = "UPDATE Products SET ProductName = ?, ProductPrice = ?, Inventory = ? WHERE ProductID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getProductName());
            stmt.setDouble(2, p.getProductPrice());
            stmt.setInt(3, p.getInventory());
            stmt.setInt(4, p.getProductID());
            stmt.executeUpdate();
        }
    }

    public void delete(int productID) throws Exception {
        String sql = "DELETE FROM Products WHERE ProductID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productID);
            stmt.executeUpdate();
        }
    }
}
