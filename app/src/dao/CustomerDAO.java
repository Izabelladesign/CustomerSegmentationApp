package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Customer;

public class CustomerDAO {

    public List<Customer> listAll() throws Exception {
        String sql = "SELECT * FROM Customers";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Customer> list = new ArrayList<>();

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("CustomerEmail"),
                        rs.getString("CustomerStatus")
                );
                list.add(c);
            }
            return list;
        }
    }

    public void insert(Customer c) throws Exception {
        String sql = "INSERT INTO Customers (FirstName, LastName, CustomerEmail, CustomerStatus) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getFirstName());
            stmt.setString(2, c.getLastName());
            stmt.setString(3, c.getCustomerEmail());
            stmt.setString(4, c.getCustomerStatus());

            stmt.executeUpdate();
        }
    }

    public void update(Customer c) throws Exception {
        String sql = "UPDATE Customers SET FirstName = ?, LastName = ?, CustomerEmail = ?, CustomerStatus = ? WHERE CustomerID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getFirstName());
            stmt.setString(2, c.getLastName());
            stmt.setString(3, c.getCustomerEmail());
            stmt.setString(4, c.getCustomerStatus());
            stmt.setInt(5, c.getCustomerID());

            stmt.executeUpdate();
        }
    }

    public void delete(int customerID) throws Exception {
        String sql = "DELETE FROM Customers WHERE CustomerID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerID);
            stmt.executeUpdate();
        }
    }

    /**
     * Gets customers by segment ID.
     */
    public List<Customer> listBySegment(int segmentID) throws Exception {
        String sql = "SELECT DISTINCT c.* FROM Customers c " +
                     "JOIN CustomerSegments cs ON c.CustomerID = cs.CustomerID " +
                     "WHERE cs.SegmentID = ? " +
                     "ORDER BY c.CustomerID";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, segmentID);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Customer> list = new ArrayList<>();
                while (rs.next()) {
                    Customer c = new Customer(
                            rs.getInt("CustomerID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("CustomerEmail"),
                            rs.getString("CustomerStatus")
                    );
                    list.add(c);
                }
                return list;
            }
        }
    }
}