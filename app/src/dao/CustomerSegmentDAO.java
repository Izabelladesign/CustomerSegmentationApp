package dao;

import db.DBConnection;
import model.CustomerSegment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerSegmentDAO {

    public List<CustomerSegment> listAll() throws Exception {
        Connection conn = DBConnection.get();
        String sql = "SELECT * FROM CustomerSegments";

        List<CustomerSegment> list = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
        }

        return list;
    }
}
