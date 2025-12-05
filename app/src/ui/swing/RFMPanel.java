package ui.swing;

import service.RfmService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RFMPanel extends JPanel {

    private final RfmService rfmService;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JButton recomputeBtn;
    private final JButton refreshBtn;

    public RFMPanel(RfmService rfmService) {
        this.rfmService = rfmService;
        setLayout(new BorderLayout(12, 12));

        tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Customer Name", "Segment", "R", "F", "M", "RFM Score", "Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        recomputeBtn = new JButton("Recompute RFM Segments");
        recomputeBtn.addActionListener(e -> recomputeRFM());

        refreshBtn = new JButton("Refresh Segments");
        refreshBtn.addActionListener(e -> loadSegments());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(recomputeBtn);
        buttonPanel.add(refreshBtn);

        // RFM Score explanation panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("About RFM Scores"));
        JTextArea infoText = new JTextArea(
            "RFM (Recency, Frequency, Monetary) Analysis:\n\n" +
            "• R (Recency): Days since last purchase. Lower = more recent.\n" +
            "• F (Frequency): Total number of orders. Higher = more frequent.\n" +
            "• M (Monetary): Total amount spent. Higher = more valuable.\n" +
            "• RFM Score: Average of R, F, M quintiles (1-5 scale). Higher score = better customer.\n\n" +
            "Segments: High-Value (top spenders), Loyal (recent + frequent), New (recent customers), " +
            "At-Risk (declining activity), Churned (inactive)."
        );
        infoText.setEditable(false);
        infoText.setBackground(this.getBackground());
        infoText.setFont(infoText.getFont().deriveFont(Font.PLAIN, 11f));
        infoText.setWrapStyleWord(true);
        infoText.setLineWrap(true);
        infoPanel.add(new JScrollPane(infoText), BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.NORTH);
        topPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        loadSegments();
    }

    private void recomputeRFM() {
        recomputeBtn.setEnabled(false);
        recomputeBtn.setText("Recomputing...");

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                rfmService.recompute();
                return null;
            }

            @Override
            protected void done() {
                recomputeBtn.setEnabled(true);
                recomputeBtn.setText("Recompute RFM Segments");
                try {
                    get();
                    showSuccess("RFM segments recomputed successfully!");
                    loadSegments();
                } catch (Exception ex) {
                    showError("Failed to recompute RFM segments: " + ex.getMessage());
                }
            }
        };

        worker.execute();
    }

    private void loadSegments() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<String> segments = rfmService.getAllSegmentsWithNames();
                tableModel.setRowCount(0);

                if (segments.isEmpty()) {
                    showInfo("No customer segments found. Click 'Recompute RFM Segments' to generate them.");
                } else {
                    for (String seg : segments) {
                        // Parse the formatted string: "Customer X: Name | Segment: Y | R=Z, F=W, M=$V | RFM Score: U | Date: D"
                        try {
                            String[] parts = seg.split("\\|");
                            if (parts.length >= 5) {
                                String customerPart = parts[0].trim(); // "Customer X: Name"
                                String segmentPart = parts[1].trim(); // "Segment: Y"
                                String rfmPart = parts[2].trim(); // "R=Z, F=W, M=$V"
                                String scorePart = parts[3].trim(); // "RFM Score: U"
                                String datePart = parts[4].trim(); // "Date: D"

                                String[] customerSplit = customerPart.split(":", 2);
                                String customerId = customerSplit[0].replace("Customer", "").trim();
                                String customerName = customerSplit.length > 1 ? customerSplit[1].trim() : "";

                                String segment = segmentPart.contains(":") ? segmentPart.split(":", 2)[1].trim() : segmentPart;
                                
                                String[] rfmValues = rfmPart.split(",");
                                String r = rfmValues[0].split("=")[1].trim();
                                String f = rfmValues[1].split("=")[1].trim();
                                String m = rfmValues[2].split("=")[1].trim();

                                String score = scorePart.contains(":") ? scorePart.split(":", 2)[1].trim() : scorePart;
                                String date = datePart.contains(":") ? datePart.split(":", 2)[1].trim() : datePart;

                                tableModel.addRow(new Object[]{
                                        customerId,
                                        customerName,
                                        segment,
                                        r,
                                        f,
                                        m,
                                        score,
                                        date
                                });
                            }
                        } catch (Exception ex) {
                            // If parsing fails, just add the raw string
                            tableModel.addRow(new Object[]{"", seg, "", "", "", "", "", ""});
                        }
                    }
                }
            } catch (Exception ex) {
                showError("Failed to load segments: " + ex.getMessage());
            }
        });
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}

