package ui.swing;

import model.Order;
import service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrdersPanel extends JPanel {

    private final OrderService orderService;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField searchCustomerIdField = new JTextField(8);
    private final JTextField createCustomerIdField = new JTextField(8);
    private final JTextField createAmountField = new JTextField(8);

    public OrdersPanel(OrderService orderService) {
        this.orderService = orderService;
        setLayout(new BorderLayout(12, 12));

        tableModel = new DefaultTableModel(new Object[]{"Order ID", "Customer ID", "Order Date", "Amount"}, 0) {
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

    private JPanel buildCreatePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Create Order"));
        panel.add(new JLabel("Customer ID:"));
        createCustomerIdField.setColumns(8);
        panel.add(createCustomerIdField);
        panel.add(new JLabel("Amount:"));
        createAmountField.setColumns(8);
        panel.add(createAmountField);
        JButton createBtn = new JButton("Create");
        createBtn.addActionListener(e -> createOrder());
        panel.add(createBtn);
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> {
            createCustomerIdField.setText("");
            createAmountField.setText("");
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

    private void createOrder() {
        String customerIdStr = createCustomerIdField.getText().trim();
        String amountStr = createAmountField.getText().trim();

        if (customerIdStr.isEmpty() || amountStr.isEmpty()) {
            showError("Customer ID and amount are required.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                int customerID = Integer.parseInt(customerIdStr);
                double amount = Double.parseDouble(amountStr);
                int orderId = orderService.createOrder(customerID, amount);
                showInfo("Order " + orderId + " created successfully.");
                createCustomerIdField.setText("");
                createAmountField.setText("");

                // auto-refresh search results if same customer
                if (customerIdStr.equals(searchCustomerIdField.getText().trim()) && !customerIdStr.isEmpty()) {
                    searchOrders();
                }
            } catch (NumberFormatException ex) {
                showError("Invalid number format for customer ID or amount.");
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

