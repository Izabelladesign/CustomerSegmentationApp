package ui.swing;

import model.Order;
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
    private final JTextField customerIdField = new JTextField(8);
    private final JTextField productIdField = new JTextField(8);
    private final JTextField quantityField = new JTextField(8);
    private final JTextField searchCustomerIdField = new JTextField(8);

    public OrdersPanel(OrderService orderService, CustomerService customerService, ProductService productService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
        setLayout(new BorderLayout(12, 12));

        tableModel = new DefaultTableModel(new Object[]{"Order ID", "Customer ID", "Order Date", "Amount"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        topPanel.add(buildSearchPanel(), BorderLayout.SOUTH);

        add(topPanel, BorderLayout.CENTER);
        add(buildOrderFormPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildOrderFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Create New Order"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Customer ID"), gbc);
        gbc.gridx = 1;
        panel.add(customerIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Product ID"), gbc);
        gbc.gridx = 1;
        panel.add(productIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Quantity"), gbc);
        gbc.gridx = 1;
        panel.add(quantityField, gbc);

        JPanel buttonPanel = new JPanel();
        JButton createBtn = new JButton("Create Order");
        createBtn.addActionListener(e -> createOrder());
        buttonPanel.add(createBtn);

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> clearForm());
        buttonPanel.add(clearBtn);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel buildSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("View Orders by Customer"));
        panel.add(new JLabel("Customer ID:"));
        panel.add(searchCustomerIdField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchOrders());
        panel.add(searchBtn);
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> {
            searchCustomerIdField.setText("");
            tableModel.setRowCount(0);
        });
        panel.add(clearBtn);
        return panel;
    }

    private void createOrder() {
        String customerIdStr = customerIdField.getText().trim();
        String productIdStr = productIdField.getText().trim();
        String quantityStr = quantityField.getText().trim();

        if (customerIdStr.isEmpty() || productIdStr.isEmpty() || quantityStr.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        try {
            int customerID = Integer.parseInt(customerIdStr);
            int productID = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);

            int orderID = orderService.createSimpleOrder(customerID, productID, quantity);
            clearForm();
            showSuccess("Order created successfully! Order ID: " + orderID);
        } catch (NumberFormatException ex) {
            showError("Invalid number format.");
        } catch (Exception ex) {
            showError("Failed to create order: " + ex.getMessage());
        }
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
                List<Order> orders = orderService.getOrdersForCustomer(customerID);
                tableModel.setRowCount(0);
                if (orders.isEmpty()) {
                    showInfo("No orders found for customer " + customerID);
                } else {
                    for (Order o : orders) {
                        tableModel.addRow(new Object[]{
                                o.getOrderID(),
                                o.getCustomerID(),
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

    private void clearForm() {
        customerIdField.setText("");
        productIdField.setText("");
        quantityField.setText("");
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

