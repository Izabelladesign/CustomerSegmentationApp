package ui.console;

import java.util.List;
import java.util.Scanner;
import model.Customer;
import model.Order;
import model.Product;
import model.RevenueBySegment;
import service.CustomerService;
import service.OrderService;
import service.ProductService;
import service.ReportService;
import service.RfmService;

public class Main {

    private static final CustomerService customerService = new CustomerService();
    private static final ProductService productService = new ProductService();
    private static final RfmService rfmService = new RfmService();
    private static final OrderService orderService = new OrderService();
    private static final ReportService reportService = new ReportService();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice = -1;

        System.out.println("=======================================");
        System.out.println("      Customer Segmentation App");
        System.out.println("=======================================");

        while (choice != 0) {
            System.out.println("\nMenu:");
            System.out.println("1. List Customers");
            System.out.println("2. Add Customer");
            System.out.println("3. List Products");
            System.out.println("4. Add Product");
            System.out.println("5. Recompute RFM Segments");
            System.out.println("6. View Customer Segments");
            System.out.println("7. View Orders for Customer");
            System.out.println("8. View Revenue by Segment");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        listCustomers();
                        break;
                    case 2:
                        addCustomer(sc);
                        break;
                    case 3:
                        listProducts();
                        break;
                    case 4:
                        addProduct(sc);
                        break;
                    case 5:
                        recomputeRFM();
                        break;
                    case 6:
                        viewCustomerSegments();
                        break;
                    case 7:
                        viewOrdersForCustomer(sc);
                        break;
                    case 8:
                        viewRevenueBySegment();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        sc.close();
    }

    // ----- menu handlers -----

    private static void listCustomers() throws Exception {
        System.out.println("\n--- Customers ---");
        List<Customer> customers = customerService.getAll();
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    private static void addCustomer(Scanner sc) throws Exception {
        System.out.print("First name: ");
        String first = sc.nextLine();
        System.out.print("Last name: ");
        String last = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Status (Active/Inactive): ");
        String status = sc.nextLine();

        customerService.addCustomer(first, last, email, status);
        System.out.println("Customer added.");
    }

    private static void listProducts() throws Exception {
        System.out.println("\n--- Products ---");
        List<Product> products = productService.getAll();
        for (Product p : products) {
            System.out.println(p);
        }
    }

    private static void addProduct(Scanner sc) throws Exception {
        System.out.print("Product name: ");
        String name = sc.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(sc.nextLine());

        productService.addProduct(name, price);
        System.out.println("Product added.");
    }

    private static void recomputeRFM() throws Exception {
        System.out.println("\nRecomputing RFM segments...");
        rfmService.recompute();
        System.out.println("RFM recompute complete!");
    }

    private static void viewCustomerSegments() throws Exception {
        System.out.println("\n--- Customer Segments ---");
        List<String> segments = rfmService.getAllSegmentsWithNames();
        if (segments.isEmpty()) {
            System.out.println("No customer segments found. Run 'Recompute RFM Segments' first.");
        } else {
            for (String seg : segments) {
                System.out.println(seg);
            }
            System.out.println("\nTotal: " + segments.size() + " customer segment(s)");
        }
    }

    private static void viewOrdersForCustomer(Scanner sc) throws Exception {
        System.out.println("\n--- Orders for Customer ---");
        System.out.print("Customer ID: ");
        int customerID = Integer.parseInt(sc.nextLine());

        List<Order> orders = orderService.getOrdersForCustomer(customerID);
        if (orders.isEmpty()) {
            System.out.println("No orders found for customer " + customerID);
        } else {
            for (Order o : orders) {
                System.out.println(o);
            }
        }
    }

    private static void viewRevenueBySegment() throws Exception {
        System.out.println("\n--- Revenue by Segment ---");
        List<RevenueBySegment> rows = reportService.getRevenueBySegment();
        if (rows.isEmpty()) {
            System.out.println("No data in v_revenue_by_segment (did you run rfm_refresh.sql and reports.sql?).");
        } else {
            for (RevenueBySegment r : rows) {
                System.out.println(r);
            }
        }
    }
}
