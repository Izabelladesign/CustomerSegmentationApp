package ui.swing;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import service.CustomerService;
import service.OrderService;
import service.ProductService;
import service.ReportService;
import service.RfmService;
import service.SegmentService;

/**
 * Main application window that hosts the different feature panels.
 */
public class DashboardFrame extends JFrame {

    private final CustomerService customerService = new CustomerService();
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    private final RfmService rfmService = new RfmService();
    private final ReportService reportService = new ReportService();
    private final SegmentService segmentService = new SegmentService();

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
        tabs.addTab("Orders", new OrdersPanel(orderService));
        tabs.addTab("Segments", new SegmentsPanel(segmentService));
        tabs.addTab("RFM Segments", new RFMPanel(rfmService));
        tabs.addTab("Reports", new ReportsPanel(reportService));

        add(tabs, BorderLayout.CENTER);
    }
}

