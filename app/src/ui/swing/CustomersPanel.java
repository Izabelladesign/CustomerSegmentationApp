package ui.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.Customer;
import service.CustomerService;

/**
 * Basic customer management panel.
 * Allows viewing and adding customers using the existing service layer.
 */
public class CustomersPanel extends JPanel {

    private final CustomerService customerService;
    private final DefaultTableModel tableModel;
    private final JTable table;

    private final JTextField firstNameField = new JTextField(12);
    private final JTextField lastNameField = new JTextField(12);
    private final JTextField emailField = new JTextField(16);
    private final JTextField statusField = new JTextField(10);
    private final JTextField idField = new JTextField(8);

    public CustomersPanel(CustomerService customerService) {
        this.customerService = customerService;
        setLayout(new BorderLayout(12, 12));

        tableModel = new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "Email", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildFormPanel(), BorderLayout.SOUTH);

        // Add selection listener for editing
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row, 0).toString());
                firstNameField.setText(tableModel.getValueAt(row, 1).toString());
                lastNameField.setText(tableModel.getValueAt(row, 2).toString());
                emailField.setText(tableModel.getValueAt(row, 3).toString());
                statusField.setText(tableModel.getValueAt(row, 4).toString());
            }
        });

        loadCustomers();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add/Edit Customer"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ID (for edit)"), gbc);
        gbc.gridx = 1;
        idField.setEditable(false);
        idField.setBackground(java.awt.Color.LIGHT_GRAY);
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("First Name"), gbc);
        gbc.gridx = 1;
        panel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Last Name"), gbc);
        gbc.gridx = 1;
        panel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Email"), gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Status"), gbc);
        gbc.gridx = 1;
        panel.add(statusField, gbc);

        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add Customer");
        addBtn.addActionListener(e -> addCustomer());
        buttonPanel.add(addBtn);

        JButton updateBtn = new JButton("Update");
        updateBtn.addActionListener(e -> updateCustomer());
        buttonPanel.add(updateBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(e -> deleteCustomer());
        buttonPanel.add(deleteBtn);

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> clearForm());
        buttonPanel.add(clearBtn);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadCustomers());
        buttonPanel.add(refreshBtn);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private void loadCustomers() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<Customer> customers = customerService.getAll();
                tableModel.setRowCount(0);
                for (Customer c : customers) {
                    tableModel.addRow(new Object[]{
                            c.getCustomerID(),
                            c.getFirstName(),
                            c.getLastName(),
                            c.getEmail(),
                            c.getStatus()
                    });
                }
            } catch (Exception ex) {
                showError("Failed to load customers: " + ex.getMessage());
            }
        });
    }

    private void addCustomer() {
        String first = firstNameField.getText().trim();
        String last = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String status = statusField.getText().trim();

        if (first.isEmpty() || last.isEmpty() || email.isEmpty() || status.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        try {
            customerService.addCustomer(first, last, email, status);
            clearForm();
            loadCustomers();
            showSuccess("Customer added successfully!");
        } catch (Exception ex) {
            showError("Failed to add customer: " + ex.getMessage());
        }
    }

    private void updateCustomer() {
        String idStr = idField.getText().trim();
        if (idStr.isEmpty()) {
            showError("Please select a customer from the table to update.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            String first = firstNameField.getText().trim();
            String last = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String status = statusField.getText().trim();

            if (first.isEmpty() || last.isEmpty() || email.isEmpty() || status.isEmpty()) {
                showError("All fields are required.");
                return;
            }

            customerService.updateCustomer(id, first, last, email, status);
            clearForm();
            loadCustomers();
            showSuccess("Customer updated successfully!");
        } catch (NumberFormatException ex) {
            showError("Invalid customer ID.");
        } catch (Exception ex) {
            showError("Failed to update customer: " + ex.getMessage());
        }
    }

    private void deleteCustomer() {
        String idStr = idField.getText().trim();
        if (idStr.isEmpty()) {
            showError("Please select a customer from the table to delete.");
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete customer ID " + idStr + "?",
            "Confirm Delete",
            javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            customerService.deleteCustomer(id);
            clearForm();
            loadCustomers();
            showSuccess("Customer deleted successfully!");
        } catch (NumberFormatException ex) {
            showError("Invalid customer ID.");
        } catch (Exception ex) {
            showError("Failed to delete customer: " + ex.getMessage());
        }
    }

    private void clearForm() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        statusField.setText("");
        table.clearSelection();
    }

    private void showError(String message) {
        javax.swing.JOptionPane.showMessageDialog(this, message, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        javax.swing.JOptionPane.showMessageDialog(this, message, "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
}

