package dao;

import db.DBConnection;
import model.CustomerSegment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerSegmentDAO {

    /**
     * Lists all customer segments with customer and segment names for display.
     * Returns a formatted string representation.
     */
    public List<CustomerSegment> listAll() throws Exception {
        String sql = "SELECT cs.* FROM CustomerSegments cs ORDER BY cs.CustomerID";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<CustomerSegment> list = new ArrayList<>();

            while (rs.next()) {
                CustomerSegment cs = new CustomerSegment(
                        rs.getInt("ID"),
                        rs.getInt("CustomerID"),
                        rs.getInt("SegmentID"),
                        rs.getDate("AsOfDate"),
                        rs.getInt("R"),
                        rs.getInt("F"),
                        rs.getDouble("M"),
                        rs.getInt("RFMScore")
                );
                list.add(cs);
            }
            return list;
        }
    }

    /**
     * Gets customer segments with customer and segment names joined for better display.
     */
    public List<String> listAllWithNames() throws Exception {
        String sql = "SELECT " +
                     "cs.CustomerID, " +
                     "CONCAT(c.FirstName, ' ', c.LastName) AS CustomerName, " +
                     "s.SegmentName, " +
                     "cs.R, cs.F, cs.M, cs.RFMScore, cs.AsOfDate " +
                     "FROM CustomerSegments cs " +
                     "JOIN Customers c ON cs.CustomerID = c.CustomerID " +
                     "JOIN Segments s ON cs.SegmentID = s.SegmentID " +
                     "ORDER BY cs.CustomerID";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<String> list = new ArrayList<>();

            while (rs.next()) {
                String line = String.format(
                    "Customer %d: %s | Segment: %s | R=%d, F=%d, M=$%.2f | RFM Score: %d | Date: %s",
                    rs.getInt("CustomerID"),
                    rs.getString("CustomerName"),
                    rs.getString("SegmentName"),
                    rs.getInt("R"),
                    rs.getInt("F"),
                    rs.getDouble("M"),
                    rs.getInt("RFMScore"),
                    rs.getDate("AsOfDate")
                );
                list.add(line);
            }
            return list;
        }
    }
}
