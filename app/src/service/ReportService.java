package service;

import dao.ReportsDAO;
import model.RevenueBySegment;

import java.util.List;

public class ReportService {

    private final ReportsDAO reportsDAO = new ReportsDAO();

    public List<RevenueBySegment> getRevenueBySegment() throws Exception {
        return reportsDAO.revenueBySegment();
    }
}
