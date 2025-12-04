package ui.swing;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Product;
import service.ProductService;

public class ProductsPanel extends JPanel {

    private final ProductService productService;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField idField = new JTextField(8);
    private final JTextField nameField = new JTextField(20);
    private final JTextField priceField = new JTextField(12);
    private final JTextField inventoryField = new JTextField(12);

    public ProductsPanel(ProductService productService) {
        this.productService = productService;
        setLayout(new BorderLayout(12, 12));

        tableModel = new DefaultTableModel(new Object[]{"ID", "Product Name", "Price", "Inventory"}, 0) {
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
                priceField.setText(tableModel.getValueAt(row, 2).toString().replace("$", ""));
                inventoryField.setText(tableModel.getValueAt(row, 3).toString());
            }
        });

        loadProducts();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add/Edit Product"));

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
        panel.add(new JLabel("Product Name"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Price"), gbc);
        gbc.gridx = 1;
        panel.add(priceField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Inventory"), gbc);
        gbc.gridx = 1;
        panel.add(inventoryField, gbc);

        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add Product");
        addBtn.addActionListener(e -> addProduct());
        buttonPanel.add(addBtn);

        JButton updateBtn = new JButton("Update");
        updateBtn.addActionListener(e -> updateProduct());
        buttonPanel.add(updateBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(e -> deleteProduct());
        buttonPanel.add(deleteBtn);

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> clearForm());
        buttonPanel.add(clearBtn);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadProducts());
        buttonPanel.add(refreshBtn);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private void loadProducts() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<Product> products = productService.getAll();
                tableModel.setRowCount(0);
                for (Product p : products) {
                    tableModel.addRow(new Object[]{
                            p.getProductID(),
                            p.getProductName(),
                            String.format("$%.2f", p.getProductPrice()),
                            p.getInventory()
                    });
                }
            } catch (Exception ex) {
                showError("Failed to load products: " + ex.getMessage());
            }
        });
    }

    private void addProduct() {
        String name = nameField.getText().trim();
        String priceStr = priceField.getText().trim();
        String invStr = inventoryField.getText().trim();

        if (name.isEmpty() || priceStr.isEmpty() || invStr.isEmpty()) {
            showError("Name, Price, and Inventory are required.");
            return;
        }
        try {
            double price = Double.parseDouble(priceStr);
            int inventory = Integer.parseInt(invStr);
        
            if (price < 0) {
                showError("Price must be a positive number.");
                return;
            }
            if (inventory < 0) {
                showError("Inventory must be a non-negative whole number.");
                return;
            }
        
            productService.addProduct(name, price, inventory);
            clearForm();
            loadProducts();
            showSuccess("Product added successfully!");
        } catch (NumberFormatException ex) {
            showError("Invalid number format for price or inventory.");
        } catch (Exception ex) {
            showError("Failed to add product: " + ex.getMessage());
        }

        try {
            double price = Double.parseDouble(priceStr);
            int inventory = invStr.isEmpty() ? 0 : Integer.parseInt(invStr);
            productService.addProduct(name, price, inventory);
            clearForm();
            loadProducts();
            showSuccess("Product added successfully!");
        } catch (NumberFormatException ex) {
            showError("Invalid number format.");
        } catch (Exception ex) {
            showError("Failed to add product: " + ex.getMessage());
        }
    }

    private void updateProduct() {
        String idStr = idField.getText().trim();
        if (idStr.isEmpty()) {
            showError("Please select a product from the table to update.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            String name = nameField.getText().trim();
            String priceStr = priceField.getText().trim();
            String invStr = inventoryField.getText().trim();

            if (name.isEmpty() || priceStr.isEmpty()) {
                showError("Name and Price are required.");
                return;
            }

            double price = Double.parseDouble(priceStr);
            int inventory = invStr.isEmpty() ? 0 : Integer.parseInt(invStr);
            productService.updateProduct(id, name, price, inventory);
            clearForm();
            loadProducts();
            showSuccess("Product updated successfully!");
        } catch (NumberFormatException ex) {
            showError("Invalid ID or price format.");
        } catch (Exception ex) {
            showError("Failed to update product: " + ex.getMessage());
        }
    }

    private void deleteProduct() {
        String idStr = idField.getText().trim();
        if (idStr.isEmpty()) {
            showError("Please select a product from the table to delete.");
            return;
        }

        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this product?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (option != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            productService.deleteProduct(id);
            clearForm();
            loadProducts();
            showSuccess("Product deleted successfully!");
        } catch (NumberFormatException ex) {
            showError("Invalid product ID format.");
        } catch (Exception ex) {
            showError("Failed to delete product: " + ex.getMessage());
        }
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        priceField.setText("");
        inventoryField.setText("");
        table.clearSelection();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

