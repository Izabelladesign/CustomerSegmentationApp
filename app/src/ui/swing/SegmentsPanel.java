package ui.swing;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Segment;
import service.SegmentService;

public class SegmentsPanel extends JPanel {

    private final SegmentService segmentService;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField idField = new JTextField(8);
    private final JTextField nameField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(40);

    public SegmentsPanel(SegmentService segmentService) {
        this.segmentService = segmentService;
        setLayout(new BorderLayout(12, 12));

        tableModel = new DefaultTableModel(new Object[]{"ID", "Segment Name", "Description"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildFormPanel(), BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row, 0).toString());
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                descriptionField.setText(tableModel.getValueAt(row, 2).toString());
            }
        });

        loadSegments();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add/Edit Segment"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID (for edit)"), gbc);
        gbc.gridx = 1;
        idField.setEditable(false);
        idField.setBackground(Color.LIGHT_GRAY);
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Segment Name"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Description"), gbc);
        gbc.gridx = 1;
        panel.add(descriptionField, gbc);

        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add Segment");
        addBtn.addActionListener(e -> addSegment());
        buttonPanel.add(addBtn);

        JButton updateBtn = new JButton("Update Segment");
        updateBtn.addActionListener(e -> updateSegment());
        buttonPanel.add(updateBtn);

        JButton deleteBtn = new JButton("Delete Segment");
        deleteBtn.addActionListener(e -> deleteSegment());
        buttonPanel.add(deleteBtn);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadSegments());
        buttonPanel.add(refreshBtn);

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> clearFields());
        buttonPanel.add(clearBtn);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private void loadSegments() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<Segment> segments = segmentService.getAll();
                tableModel.setRowCount(0);
                for (Segment s : segments) {
                    tableModel.addRow(new Object[]{
                            s.getSegmentID(),
                            s.getSegmentName(),
                            s.getDescription()
                    });
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to load segments: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void addSegment() {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();

        if (name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Segment name and description are required.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                segmentService.addSegment(name, description);
                JOptionPane.showMessageDialog(this, "Segment added successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadSegments();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to add segment: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void updateSegment() {
        String idStr = idField.getText().trim();
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();

        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a segment to update.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Segment name and description are required.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                int segmentID = Integer.parseInt(idStr);
                segmentService.updateSegment(segmentID, name, description);
                JOptionPane.showMessageDialog(this, "Segment updated successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadSegments();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid segment ID.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to update segment: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void deleteSegment() {
        String idStr = idField.getText().trim();

        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a segment to delete.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this segment?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> {
                try {
                    int segmentID = Integer.parseInt(idStr);
                    segmentService.deleteSegment(segmentID);
                    JOptionPane.showMessageDialog(this, "Segment deleted successfully.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    loadSegments();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid segment ID.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Failed to delete segment: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        descriptionField.setText("");
    }
}

