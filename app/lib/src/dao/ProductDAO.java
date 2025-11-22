package dao;

import db.DBConnection;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import model.Product;

public class ProductDAO {

    public List<Product> listAll() throws Exception {
        Connection conn = DBConnection.get();
        String sql = "SELECT * FROM Products";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Product> list = new ArrayList<>();

        while (rs.next()) {
            Product p = new Product(
                rs.getInt("ProductID"),
                rs.getString("ProductName"),
                rs.getDouble("UnitPrice")
            );
            list.add(p);
        }
        return list;
    }

    public void insert(Product p) throws Exception {
        Connection conn = DBConnection.get();
        String sql = "INSERT INTO Products (ProductName, UnitPrice) VALUES (?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, p.getProductName());
        stmt.setDouble(2, p.getUnitPrice());

        stmt.executeUpdate();
    }
}