package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class ProductDAO {

    public List<Product> listAll() throws Exception {
        String sql = "SELECT ProductID, ProductName, ProductPrice AS UnitPrice FROM Products";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Product> list = new ArrayList<>();

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("UnitPrice")   // uses the alias
                );
                list.add(p);
            }
            return list;
        }
    }
    
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
                            rs.getDouble("ProductPrice")
                    );
                } else {
                    return null;
                }
            }
        }
    }


    public void insert(Product p) throws Exception {
        // use ProductPrice here, not UnitPrice
        String sql = "INSERT INTO Products (ProductName, ProductPrice) VALUES (?, ?)";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getProductName());
            stmt.setDouble(2, p.getUnitPrice());
            stmt.executeUpdate();
        }
    }

    public void update(Product p) throws Exception {
        String sql = "UPDATE Products SET ProductName = ?, ProductPrice = ? WHERE ProductID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getProductName());
            stmt.setDouble(2, p.getProductPrice());
            stmt.setInt(3, p.getProductID());
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
