package dao;

import db.DBConnection;
import model.Segment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SegmentDAO {
    /**
     * Loads all segments from the database.
     * Ordered by SegmentID so the list stays consistent in the UI.
     */
    public List<Segment> listAll() throws Exception {
        Connection conn = DBConnection.get();
        String sql = "SELECT * FROM Segments ORDER BY SegmentID";

        List<Segment> list = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Segment s = new Segment(
                        rs.getInt("SegmentID"),
                        rs.getString("SegmentName"),
                        rs.getString("Description")
                );
                list.add(s);
            }
        }

        return list;
    }
    /**
     * Adds a new segment to the table.
     * Used mostly when defining new RFM categories.
     */
    public void insert(String segmentName, String description) throws Exception {
        String sql = "INSERT INTO Segments (SegmentName, Description) VALUES (?, ?)";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, segmentName);
            stmt.setString(2, description);
            stmt.executeUpdate();
        }
    }
    /**
     * Updates an existing segment’s name or description.
     */
    public void update(int segmentID, String segmentName, String description) throws Exception {
        String sql = "UPDATE Segments SET SegmentName = ?, Description = ? WHERE SegmentID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, segmentName);
            stmt.setString(2, description);
            stmt.setInt(3, segmentID);
            stmt.executeUpdate();
        }
    }
    /**
     * Deletes a segment by ID.
     * Any linked customer segment records are handled by the database
     * through foreign key rules (cascade).
     */
    public void delete(int segmentID) throws Exception {
        String sql = "DELETE FROM Segments WHERE SegmentID = ?";

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, segmentID);
            stmt.executeUpdate();
        }
    }
}
