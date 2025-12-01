package dao;

import db.DBConnection;
import model.Segment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SegmentDAO {

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
}
