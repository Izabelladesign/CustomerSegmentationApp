package dao;

import db.DBConnection;
import model.OrderItem;
import model.OrderItemWithProduct;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    /**
     * Inserts a new order item for a given order.
     * Each row represents one product in the order.
     */
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
    /**
     * Returns all order items for a specific order.
     * Useful when loading the details of an order.
     */
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
    /**
     * Updates the quantity for an existing order item.
     * The LineTotal recalculates automatically because it’s a generated column in SQL.
     */
    public void updateQuantity(int orderItemID, int newQuantity) throws Exception {
        String sql = "UPDATE OrderItems SET Quantity = ? WHERE OrderItemID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, orderItemID);
            stmt.executeUpdate();
        }
    }
     /**
     * Deletes one order item.
     * If the parent order is deleted, this would also be removed
     * automatically because of ON DELETE CASCADE on OrderID.
     */
    public void deleteItem(int orderItemID) throws Exception {
        String sql = "DELETE FROM OrderItems WHERE OrderItemID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderItemID);
            stmt.executeUpdate();
        }
    }

    /**
     * Gets order items with product names for a specific order.
     */
    public List<OrderItemWithProduct> listByOrderWithProduct(int orderID) throws Exception {
        String sql = "SELECT oi.OrderItemID, oi.OrderID, oi.ProductID, oi.Quantity, oi.UnitPrice, oi.LineTotal, " +
                     "p.ProductName " +
                     "FROM OrderItems oi " +
                     "JOIN Products p ON oi.ProductID = p.ProductID " +
                     "WHERE oi.OrderID = ? " +
                     "ORDER BY oi.OrderItemID";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderID);

            try (ResultSet rs = stmt.executeQuery()) {
                List<OrderItemWithProduct> items = new ArrayList<>();
                while (rs.next()) {
                    OrderItemWithProduct item = new OrderItemWithProduct(
                            rs.getInt("OrderItemID"),
                            rs.getInt("OrderID"),
                            rs.getInt("ProductID"),
                            rs.getString("ProductName"),
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
}
