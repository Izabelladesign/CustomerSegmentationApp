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

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        topPanel.add(buildSearchPanel(), BorderLayout.NORTH);

        add(topPanel, BorderLayout.CENTER);
        
        // Note: Order creation removed - customers create orders, not admins
        JLabel infoLabel = new JLabel("<html><center><b>Orders View Only</b><br>Customers create orders through their interface.<br>Use the search below to view orders by customer ID.</center></html>", SwingConstants.CENTER);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.SOUTH);
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


    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}

