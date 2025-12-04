package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.OrderWithCustomer;

public class OrderDAO {

    public int insertOrder(int customerID, double orderAmount) throws Exception {
        String sql = "INSERT INTO Orders (CustomerID, OrderDate, OrderAmount) VALUES (?, NOW(), ?)";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, customerID);
            stmt.setDouble(2, orderAmount);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new RuntimeException("Failed to get generated OrderID");
            }
        }
    }

    public List<Order> listByCustomer(int customerID) throws Exception {
        String sql = "SELECT * FROM Orders WHERE CustomerID = ? ORDER BY OrderDate DESC";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerID);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Order> orders = new ArrayList<>();
                while (rs.next()) {
                    Order o = new Order(
                            rs.getInt("OrderID"),
                            rs.getInt("CustomerID"),
                            rs.getTimestamp("OrderDate"),
                            rs.getDouble("OrderAmount")
                    );
                    orders.add(o);
                }
                return orders;
            }
        }
    }

    public void updateOrderAmount(int orderID, double orderAmount) throws Exception {
        String sql = "UPDATE Orders SET OrderAmount = ? WHERE OrderID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, orderAmount);
            stmt.setInt(2, orderID);
            stmt.executeUpdate();
        }
    }

    public void deleteOrder(int orderID) throws Exception {
        String sql = "DELETE FROM Orders WHERE OrderID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderID);
            stmt.executeUpdate();
        }
    }

    /**
     * Gets all orders with customer names.
     */
    public List<OrderWithCustomer> listAllWithCustomer() throws Exception {
        String sql = "SELECT o.OrderID, o.CustomerID, o.OrderDate, o.OrderAmount, " +
                     "CONCAT(c.FirstName, ' ', c.LastName) AS CustomerName " +
                     "FROM Orders o " +
                     "JOIN Customers c ON o.CustomerID = c.CustomerID " +
                     "ORDER BY o.OrderDate DESC";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<OrderWithCustomer> orders = new ArrayList<>();
            while (rs.next()) {
                OrderWithCustomer o = new OrderWithCustomer(
                        rs.getInt("OrderID"),
                        rs.getInt("CustomerID"),
                        rs.getString("CustomerName"),
                        rs.getTimestamp("OrderDate"),
                        rs.getDouble("OrderAmount")
                );
                orders.add(o);
            }
            return orders;
        }
    }

    /**
     * Gets orders for a specific customer with customer name.
     */
    public List<OrderWithCustomer> listByCustomerWithName(int customerID) throws Exception {
        String sql = "SELECT o.OrderID, o.CustomerID, o.OrderDate, o.OrderAmount, " +
                     "CONCAT(c.FirstName, ' ', c.LastName) AS CustomerName " +
                     "FROM Orders o " +
                     "JOIN Customers c ON o.CustomerID = c.CustomerID " +
                     "WHERE o.CustomerID = ? " +
                     "ORDER BY o.OrderDate DESC";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerID);

            try (ResultSet rs = stmt.executeQuery()) {
                List<OrderWithCustomer> orders = new ArrayList<>();
                while (rs.next()) {
                    OrderWithCustomer o = new OrderWithCustomer(
                            rs.getInt("OrderID"),
                            rs.getInt("CustomerID"),
                            rs.getString("CustomerName"),
                            rs.getTimestamp("OrderDate"),
                            rs.getDouble("OrderAmount")
                    );
                    orders.add(o);
                }
                return orders;
            }
        }
    }
}
