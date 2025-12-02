package ui.swing;

import service.CustomerService;
import service.ProductService;
import service.OrderService;
import service.RfmService;
import service.ReportService;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

/**
 * Main application window that hosts the different feature panels.
 */
public class DashboardFrame extends JFrame {

    private final CustomerService customerService = new CustomerService();
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    private final RfmService rfmService = new RfmService();
    private final ReportService reportService = new ReportService();

    public DashboardFrame() {
        super("Customer Segmentation - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Customers", new CustomersPanel(customerService));
        tabs.addTab("Products", new ProductsPanel(productService));
        tabs.addTab("Orders", new OrdersPanel(orderService, customerService, productService));
        tabs.addTab("RFM Segments", new RFMPanel(rfmService));
        tabs.addTab("Reports", new ReportsPanel(reportService));

        add(tabs, BorderLayout.CENTER);
    }
}

