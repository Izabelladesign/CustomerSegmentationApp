package dao;

import db.DBConnection;
import model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {

    public void insertItem(int orderID, int productID, int quantity, double unitPrice) throws Exception {
        Connection conn = DBConnection.get();
        String sql = "INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitPrice) " +
                     "VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderID);
            stmt.setInt(2, productID);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, unitPrice);
            stmt.executeUpdate();
        }
    }

    public List<OrderItem> listByOrder(int orderID) throws Exception {
        Connection conn = DBConnection.get();
        String sql = "SELECT * FROM OrderItems WHERE OrderID = ?";

        List<OrderItem> items = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem(
                            rs.getInt("OrderItemID"),
                            rs.getInt("OrderID"),
                            rs.getInt("ProductID"),
                            rs.getInt("Quantity"),
                            rs.getDouble("UnitPrice"),
                            rs.getDouble("LineTotal")
                    );
                    items.add(item);
                }
            }
        }

        return items;
    }
}
