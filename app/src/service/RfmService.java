package service;

import db.SqlRunner;
import db.DBConnection;
import dao.CustomerSegmentDAO;

import java.sql.Connection;
import java.util.List;

public class RfmService {

    private final CustomerSegmentDAO customerSegmentDAO = new CustomerSegmentDAO();

    /**
     * Recomputes RFM segments for all customers.
     * First creates/updates the customer metrics view, then refreshes the segment assignments.
     * 
     * Prerequisites: Segments table must be populated with at least SegmentIDs 1-5:
     *   1 = Loyal, 2 = New, 3 = At-Risk, 4 = Churned, 5 = High-Value
     */
    public void recompute() throws Exception {
        try (Connection conn = DBConnection.get()) {
            // First, ensure the view exists (creates or updates it)
            SqlRunner.runFile(conn, "sql/rfm_views.sql");
            
            // Then, refresh the customer segments based on the view
            SqlRunner.runFile(conn, "sql/rfm_refresh.sql");
        }
    }

    /**
     * Gets all customer segments with customer and segment names for display.
     */
    public List<String> getAllSegmentsWithNames() throws Exception {
        return customerSegmentDAO.listAllWithNames();
    }
}