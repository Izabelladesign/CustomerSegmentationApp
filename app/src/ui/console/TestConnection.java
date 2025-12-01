package ui.console;

import db.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConnection {
    public static void main(String[] args) throws Exception {

        Connection conn = DBConnection.get();
        System.out.println("Connected successfully!");

        // test query
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Customers LIMIT 3");

        while (rs.next()) {
            System.out.println(
                rs.getInt("CustomerID") + " - " +
                rs.getString("FirstName") + " " +
                rs.getString("LastName")
            );
        }

        conn.close();
    }
}