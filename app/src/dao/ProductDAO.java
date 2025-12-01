package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class ProductDAO {

    public List<Product> listAll() throws Exception {
        Connection conn = DBConnection.get();
        // alias ProductPrice AS UnitPrice so it matches your Java field name
        String sql = "SELECT ProductID, ProductName, ProductPrice AS UnitPrice FROM Products";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Product> list = new ArrayList<>();

        while (rs.next()) {
            Product p = new Product(
                rs.getInt("ProductID"),
                rs.getString("ProductName"),
                rs.getDouble("UnitPrice")   // uses the alias
            );
            list.add(p);
        }
        rs.close();
        stmt.close();
        conn.close();
        return list;
    }
    
    public Product findById(int productID) throws Exception {
        Connection conn = DBConnection.get();
        String sql = "SELECT * FROM Products WHERE ProductID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        Connection conn = DBConnection.get();
        // use ProductPrice here, not UnitPrice
        String sql = "INSERT INTO Products (ProductName, ProductPrice) VALUES (?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, p.getProductName());
        stmt.setDouble(2, p.getUnitPrice());
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
