package ui.swing;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.OrderWithCustomer;
import service.CustomerService;
import service.OrderService;
import service.ProductService;

public class OrdersPanel extends JPanel {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField searchCustomerIdField = new JTextField(8);
    private final JTextField createCustomerIdField = new JTextField(8);
    private final JTextField createCustomerNameField = new JTextField(15);
    private final JTextField createProductIdField = new JTextField(8);
    private final JTextField createProductNameField = new JTextField(15);
    private final JTextField createQuantityField = new JTextField(5);
    private final JTextField createPriceField = new JTextField(10);
    private final JTextField createDateField = new JTextField(20);

    public OrdersPanel(OrderService orderService) {
        this.orderService = orderService;
        this.customerService = new CustomerService();
        this.productService = new ProductService();
        setLayout(new BorderLayout(12, 12));

        tableModel = new DefaultTableModel(new Object[]{"Order ID", "Customer ID", "Customer Name", "Product Info", "Order Date", "Amount"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        
        // Cancel order button will be created in buildSearchPanel

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        centerPanel.add(buildSearchPanel(), BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buildCreatePanel(), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }


    private JPanel buildSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("View Orders"));
        panel.add(new JLabel("Customer ID:"));
        panel.add(searchCustomerIdField);
        JButton searchBtn = new JButton("Search by Customer");
        searchBtn.addActionListener(e -> searchOrders());
        panel.add(searchBtn);
        JButton viewAllBtn = new JButton("View All Orders");
        viewAllBtn.addActionListener(e -> viewAllOrders());
        panel.add(viewAllBtn);
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> {
            searchCustomerIdField.setText("");
            tableModel.setRowCount(0);
        });
        panel.add(clearBtn);
        JButton cancelOrderBtn = new JButton("Cancel Selected Order");
        cancelOrderBtn.addActionListener(e -> cancelSelectedOrder());
        panel.add(cancelOrderBtn);
        return panel;
    }

    private JPanel buildCreatePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Create Order"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Customer section
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Customer ID:"), gbc);
        gbc.gridx = 1;
        createCustomerIdField.setColumns(8);
        panel.add(createCustomerIdField, gbc);
        createCustomerIdField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                lookupCustomerName();
            }
        });
        
        gbc.gridx = 2;
        panel.add(new JLabel("Customer Name:"), gbc);
        gbc.gridx = 3;
        createCustomerNameField.setColumns(15);
        createCustomerNameField.setEditable(false);
        panel.add(createCustomerNameField, gbc);
        
        // Product section
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Product ID:"), gbc);
        gbc.gridx = 1;
        createProductIdField.setColumns(8);
        panel.add(createProductIdField, gbc);
        createProductIdField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                lookupProductName();
            }
        });
        
        gbc.gridx = 2;
        panel.add(new JLabel("Product Name:"), gbc);
        gbc.gridx = 3;
        createProductNameField.setColumns(15);
        createProductNameField.setEditable(false);
        panel.add(createProductNameField, gbc);
        
        // Quantity
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        createQuantityField.setColumns(5);
        createQuantityField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                updatePriceFromQuantity();
            }
        });
        panel.add(createQuantityField, gbc);
        
        // Price/Amount
        gbc.gridx = 2;
        panel.add(new JLabel("Price/Amount:"), gbc);
        gbc.gridx = 3;
        createPriceField.setColumns(10);
        panel.add(createPriceField, gbc);
        
        // Date
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Date (YYYY-MM-DD HH:MM:SS):"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        createDateField.setColumns(20);
        createDateField.setText(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        panel.add(createDateField, gbc);
        
        // Buttons at the bottom
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton createBtn = new JButton("Create Order");
        createBtn.addActionListener(e -> createOrder());
        buttonPanel.add(createBtn);
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> {
            createCustomerIdField.setText("");
            createCustomerNameField.setText("");
            createProductIdField.setText("");
            createProductNameField.setText("");
            createQuantityField.setText("");
            createPriceField.setText("");
            createDateField.setText(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        });
        buttonPanel.add(clearBtn);
        panel.add(buttonPanel, gbc);
        
        return panel;
    }

    private void searchOrders() {
        String customerIdStr = searchCustomerIdField.getText().trim();
        if (customerIdStr.isEmpty()) {
            showError("Please enter a customer ID.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                int customerID = Integer.parseInt(customerIdStr);
                List<OrderWithCustomer> orders = orderService.getOrdersForCustomerWithName(customerID);
                tableModel.setRowCount(0);
                if (orders.isEmpty()) {
                    showInfo("No orders found for customer " + customerID);
                } else {
                    for (OrderWithCustomer o : orders) {
                        tableModel.addRow(new Object[]{
                                o.getOrderID(),
                                o.getCustomerID(),
                                o.getCustomerName(),
                                o.getProductInfo().isEmpty() ? "No products" : o.getProductInfo(),
                                o.getOrderDate(),
                                String.format("$%.2f", o.getOrderAmount())
                        });
                    }
                }
            } catch (NumberFormatException ex) {
                showError("Invalid customer ID format.");
            } catch (Exception ex) {
                showError("Failed to load orders: " + ex.getMessage());
            }
        });
    }

    private void viewAllOrders() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<OrderWithCustomer> orders = orderService.getAllOrdersWithCustomer();
                tableModel.setRowCount(0);
                if (orders.isEmpty()) {
                    showInfo("No orders found.");
                } else {
                    for (OrderWithCustomer o : orders) {
                        tableModel.addRow(new Object[]{
                                o.getOrderID(),
                                o.getCustomerID(),
                                o.getCustomerName(),
                                o.getProductInfo().isEmpty() ? "No products" : o.getProductInfo(),
                                o.getOrderDate(),
                                String.format("$%.2f", o.getOrderAmount())
                        });
                    }
                }
            } catch (Exception ex) {
                showError("Failed to load orders: " + ex.getMessage());
            }
        });
    }

    private void lookupCustomerName() {
        String customerIdStr = createCustomerIdField.getText().trim();
        if (customerIdStr.isEmpty()) {
            createCustomerNameField.setText("");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                int customerID = Integer.parseInt(customerIdStr);
                List<model.Customer> customers = customerService.getAll();
                for (model.Customer c : customers) {
                    if (c.getCustomerID() == customerID) {
                        createCustomerNameField.setText(c.getFirstName() + " " + c.getLastName());
                        return;
                    }
                }
                createCustomerNameField.setText("Customer not found");
            } catch (NumberFormatException ex) {
                createCustomerNameField.setText("Invalid ID");
            } catch (Exception ex) {
                createCustomerNameField.setText("Error: " + ex.getMessage());
            }
        });
    }

    private void updatePriceFromQuantity() {
        String productIdStr = createProductIdField.getText().trim();
        String quantityStr = createQuantityField.getText().trim();
        
        if (productIdStr.isEmpty() || quantityStr.isEmpty()) {
            return;
        }
        
        try {
            int productID = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);
            
            List<model.Product> products = productService.getAll();
            for (model.Product p : products) {
                if (p.getProductID() == productID) {
                    double totalPrice = p.getProductPrice() * quantity;
                    createPriceField.setText(String.format("%.2f", totalPrice));
                    return;
                }
            }
        } catch (Exception ex) {
            // Ignore errors, user can manually enter price
        }
    }

    private void lookupProductName() {
        String productIdStr = createProductIdField.getText().trim();
        if (productIdStr.isEmpty()) {
            createProductNameField.setText("");
            createPriceField.setText("");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                int productID = Integer.parseInt(productIdStr);
                List<model.Product> products = productService.getAll();
                for (model.Product p : products) {
                    if (p.getProductID() == productID) {
                        createProductNameField.setText(p.getProductName());
                        // Auto-fill price, but allow manual override
                        String quantityStr = createQuantityField.getText().trim();
                        if (!quantityStr.isEmpty()) {
                            try {
                                int qty = Integer.parseInt(quantityStr);
                                double totalPrice = p.getProductPrice() * qty;
                                createPriceField.setText(String.format("%.2f", totalPrice));
                            } catch (NumberFormatException ex) {
                                createPriceField.setText(String.format("%.2f", p.getProductPrice()));
                            }
                        } else {
                            createPriceField.setText(String.format("%.2f", p.getProductPrice()));
                        }
                        return;
                    }
                }
                createProductNameField.setText("Product not found");
                createPriceField.setText("");
            } catch (NumberFormatException ex) {
                createProductNameField.setText("Invalid ID");
                createPriceField.setText("");
            } catch (Exception ex) {
                createProductNameField.setText("Error: " + ex.getMessage());
                createPriceField.setText("");
            }
        });
    }

    private void createOrder() {
        String customerIdStr = createCustomerIdField.getText().trim();
        String productIdStr = createProductIdField.getText().trim();
        String quantityStr = createQuantityField.getText().trim();
        String priceStr = createPriceField.getText().trim();
        String dateStr = createDateField.getText().trim();

        if (customerIdStr.isEmpty() || productIdStr.isEmpty()) {
            showError("Customer ID and Product ID are required.");
            return;
        }

        if (quantityStr.isEmpty()) {
            showError("Quantity is required.");
            return;
        }

        if (priceStr.isEmpty()) {
            showError("Price/Amount is required.");
            return;
        }

        if (dateStr.isEmpty()) {
            showError("Date is required.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                int customerID = Integer.parseInt(customerIdStr);
                int productID = Integer.parseInt(productIdStr);
                int quantity = Integer.parseInt(quantityStr);
                double orderAmount = Double.parseDouble(priceStr);
                
                if (quantity <= 0) {
                    showError("Quantity must be a positive number.");
                    return;
                }
                
                if (orderAmount <= 0) {
                    showError("Price/Amount must be a positive number.");
                    return;
                }

                // Parse date
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateFormat.setLenient(false);
                java.util.Date parsedDate = dateFormat.parse(dateStr);
                java.sql.Timestamp orderDate = new java.sql.Timestamp(parsedDate.getTime());
                
                int orderId = orderService.createOrderWithProductAndDate(customerID, productID, quantity, orderAmount, orderDate);
                
                showInfo("Order " + orderId + " created successfully for " + 
                        createCustomerNameField.getText() + " - " + 
                        createProductNameField.getText() + " (Qty: " + quantity + ", Amount: $" + String.format("%.2f", orderAmount) + ") on " + dateStr);
                
                // Clear all fields
                createCustomerIdField.setText("");
                createCustomerNameField.setText("");
                createProductIdField.setText("");
                createProductNameField.setText("");
                createQuantityField.setText("");
                createPriceField.setText("");
                createDateField.setText(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));

                // auto-refresh display
                viewAllOrders();
            } catch (NumberFormatException ex) {
                showError("Invalid number format. Please check Customer ID, Product ID, Quantity, and Price.");
            } catch (java.text.ParseException ex) {
                showError("Invalid date format. Please use YYYY-MM-DD HH:MM:SS (e.g., 2024-12-25 14:30:00)");
            } catch (Exception ex) {
                showError("Failed to create order: " + ex.getMessage());
            }
        });
    }

    private void cancelSelectedOrder() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showError("Please select an order to cancel.");
            return;
        }

        int orderID = (Integer) tableModel.getValueAt(selectedRow, 0);
        String customerName = (String) tableModel.getValueAt(selectedRow, 2);
        String amount = (String) tableModel.getValueAt(selectedRow, 5);

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to cancel Order #" + orderID + " for " + customerName + " (Amount: " + amount + ")?",
            "Confirm Cancel Order",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> {
                try {
                    orderService.deleteOrder(orderID);
                    showInfo("Order #" + orderID + " has been cancelled successfully.");
                    // Refresh the display
                    if (!searchCustomerIdField.getText().trim().isEmpty()) {
                        searchOrders();
                    } else {
                        viewAllOrders();
                    }
                } catch (Exception ex) {
                    showError("Failed to cancel order: " + ex.getMessage());
                }
            });
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}

