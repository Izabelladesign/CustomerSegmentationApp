package dao;

import db.DBConnection;
import model.RevenueBySegment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportsDAO {

    public List<RevenueBySegment> revenueBySegment() throws Exception {
        Connection conn = DBConnection.get();
        String sql = "SELECT SegmentName, CustomerCount, TotalRevenue FROM v_revenue_by_segment";

        List<RevenueBySegment> list = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RevenueBySegment row = new RevenueBySegment(
                        rs.getString("SegmentName"),
                        rs.getLong("CustomerCount"),
                        rs.getDouble("TotalRevenue")
                );
                list.add(row);
            }
        }

        return list;
    }
}
