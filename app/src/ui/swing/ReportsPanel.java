package ui.swing;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.RevenueBySegment;
import service.ReportService;

public class ReportsPanel extends JPanel {

    private final ReportService reportService;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public ReportsPanel(ReportService reportService) {
        this.reportService = reportService;
        setLayout(new BorderLayout(12, 12));

        tableModel = new DefaultTableModel(new Object[]{"Segment Name", "Customer Count", "Total Revenue"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshBtn = new JButton("Refresh Report");
        refreshBtn.addActionListener(e -> loadReport());
        buttonPanel.add(refreshBtn);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.NORTH);
        topPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(topPanel, BorderLayout.CENTER);

        loadReport();
    }

    private void loadReport() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<RevenueBySegment> reports = reportService.getRevenueBySegment();
                tableModel.setRowCount(0);

                if (reports.isEmpty()) {
                    showInfo("No revenue data available. Make sure RFM segments have been computed.");
                } else {
                    for (RevenueBySegment r : reports) {
                        tableModel.addRow(new Object[]{
                                r.getSegmentName(),
                                r.getCustomerCount(),
                                String.format("$%.2f", r.getTotalRevenue())
                        });
                    }
                }
            } catch (Exception ex) {
                showError("Failed to load report: " + ex.getMessage());
            }
        });
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}

