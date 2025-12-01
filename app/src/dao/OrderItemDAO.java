package dao;

import db.DBConnection;
import model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {

    public void insertItem(int orderID, int productID, int quantity, double unitPrice) throws Exception {
        String sql = "INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderID);
            stmt.setInt(2, productID);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, unitPrice);
            stmt.executeUpdate();
        }
    }

    public List<OrderItem> listByOrder(int orderID) throws Exception {
        String sql = "SELECT * FROM OrderItems WHERE OrderID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderID);

            try (ResultSet rs = stmt.executeQuery()) {
                List<OrderItem> items = new ArrayList<>();
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
                return items;
            }
        }
    }

    public void updateQuantity(int orderItemID, int newQuantity) throws Exception {
        String sql = "UPDATE OrderItems SET Quantity = ? WHERE OrderItemID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, orderItemID);
            stmt.executeUpdate();
        }
    }

    public void deleteItem(int orderItemID) throws Exception {
        String sql = "DELETE FROM OrderItems WHERE OrderItemID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderItemID);
            stmt.executeUpdate();
        }
    }
}
