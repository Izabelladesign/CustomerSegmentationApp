package ui.swing;

import model.OrderWithCustomer;
import service.OrderService;
import service.CustomerService;
import service.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

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
        return panel;
    }

    private JPanel buildCreatePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Create Order"));
        
        // Customer section
        panel.add(new JLabel("Customer ID:"));
        createCustomerIdField.setColumns(8);
        panel.add(createCustomerIdField);
        createCustomerIdField.addActionListener(e -> lookupCustomerName());
        JButton lookupCustomerBtn = new JButton("Lookup");
        lookupCustomerBtn.addActionListener(e -> lookupCustomerName());
        panel.add(lookupCustomerBtn);
        panel.add(new JLabel("Customer Name:"));
        createCustomerNameField.setColumns(15);
        createCustomerNameField.setEditable(false);
        panel.add(createCustomerNameField);
        
        // Product section
        panel.add(new JLabel("Product ID:"));
        createProductIdField.setColumns(8);
        panel.add(createProductIdField);
        createProductIdField.addActionListener(e -> lookupProductName());
        JButton lookupProductBtn = new JButton("Lookup");
        lookupProductBtn.addActionListener(e -> lookupProductName());
        panel.add(lookupProductBtn);
        panel.add(new JLabel("Product Name:"));
        createProductNameField.setColumns(15);
        createProductNameField.setEditable(false);
        panel.add(createProductNameField);
        panel.add(new JLabel("Quantity:"));
        createQuantityField.setColumns(5);
        panel.add(createQuantityField);
        
        JButton createBtn = new JButton("Create");
        createBtn.addActionListener(e -> createOrder());
        panel.add(createBtn);
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> {
            createCustomerIdField.setText("");
            createCustomerNameField.setText("");
            createProductIdField.setText("");
            createProductNameField.setText("");
            createQuantityField.setText("");
        });
        panel.add(clearBtn);
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

    private void lookupProductName() {
        String productIdStr = createProductIdField.getText().trim();
        if (productIdStr.isEmpty()) {
            createProductNameField.setText("");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                int productID = Integer.parseInt(productIdStr);
                List<model.Product> products = productService.getAll();
                for (model.Product p : products) {
                    if (p.getProductID() == productID) {
                        createProductNameField.setText(p.getProductName() + " ($" + String.format("%.2f", p.getProductPrice()) + ")");
                        return;
                    }
                }
                createProductNameField.setText("Product not found");
            } catch (NumberFormatException ex) {
                createProductNameField.setText("Invalid ID");
            } catch (Exception ex) {
                createProductNameField.setText("Error: " + ex.getMessage());
            }
        });
    }

    private void createOrder() {
        String customerIdStr = createCustomerIdField.getText().trim();
        String productIdStr = createProductIdField.getText().trim();
        String quantityStr = createQuantityField.getText().trim();

        if (customerIdStr.isEmpty() || productIdStr.isEmpty()) {
            showError("Customer ID and Product ID are required.");
            return;
        }

        // Default quantity to 1 if not provided
        int quantityValue = 1;
        if (!quantityStr.isEmpty()) {
            try {
                int parsedQuantity = Integer.parseInt(quantityStr);
                if (parsedQuantity <= 0) {
                    showError("Quantity must be a positive number.");
                    return;
                }
                quantityValue = parsedQuantity;
            } catch (NumberFormatException ex) {
                showError("Invalid quantity format. Please enter a number.");
                return;
            }
        }

        // Make quantity final for lambda expression
        final int quantity = quantityValue;
        final String customerName = createCustomerNameField.getText();
        final String productName = createProductNameField.getText();

        SwingUtilities.invokeLater(() -> {
            try {
                int customerID = Integer.parseInt(customerIdStr);
                int productID = Integer.parseInt(productIdStr);
                
                int orderId = orderService.createOrderWithProduct(customerID, productID, quantity);
                showInfo("Order " + orderId + " created successfully for " + 
                        customerName + " - " + 
                        productName + " (Qty: " + quantity + ")");
                
                // Clear all fields
                createCustomerIdField.setText("");
                createCustomerNameField.setText("");
                createProductIdField.setText("");
                createProductNameField.setText("");
                createQuantityField.setText("");

                // auto-refresh display
                viewAllOrders();
            } catch (NumberFormatException ex) {
                showError("Invalid number format for customer ID or product ID.");
            } catch (Exception ex) {
                showError("Failed to create order: " + ex.getMessage());
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

