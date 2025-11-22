package dao;

import db.DBConnection;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import model.Customer;

public class CustomerDAO {

    public List<Customer> listAll() throws Exception {
        Connection conn = DBConnection.get();
        String sql = "SELECT * FROM Customers";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

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

    public void insert(Customer c) throws Exception {
        Connection conn = DBConnection.get();
        String sql = "INSERT INTO Customers (FirstName, LastName, CustomerEmail, CustomerStatus) VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, c.getFirstName());
        stmt.setString(2, c.getLastName());
        stmt.setString(3, c.getCustomerEmail());
        stmt.setString(4, c.getCustomerStatus());

        stmt.executeUpdate();
    }
}