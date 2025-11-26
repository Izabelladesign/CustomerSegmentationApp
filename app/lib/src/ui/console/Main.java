package ui.console;

import java.util.List;
import java.util.Scanner;

import service.CustomerService;
import service.ProductService;
import service.RfmService;
import model.Customer;
import model.Product;

public class Main {

    private static CustomerService customerService = new CustomerService();
    private static ProductService productService = new ProductService();
    private static RfmService rfmService = new RfmService();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice = -1;

        System.out.println("=====================================");
        System.out.println("   Customer Segmentation Console App");
        System.out.println("=====================================");

        while (choice != 0) {
            System.out.println("\nMenu:");
            System.out.println("1. List Customers");
            System.out.println("2. Add Customer");
            System.out.println("3. List Products");
            System.out.println("4. Add Product");
            System.out.println("5. Recompute RFM Segments");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            choice = Integer.parseInt(sc.nextLine());

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

                    case 0:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        sc.close();
    }

    private static void listCustomers() throws Exception {
        System.out.println("\n--- Customers ---");
        List<Customer> list = customerService.getAll();
        for (Customer c : list) {
            System.out.println(c);
        }
    }

    private static void addCustomer(Scanner sc) throws Exception {
        System.out.println("\nAdd Customer:");
        System.out.print("First name: ");
        String f = sc.nextLine();
        System.out.print("Last name: ");
        String l = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Status (Active/Inactive): ");
        String status = sc.nextLine();

        customerService.addCustomer(f, l, email, status);
        System.out.println("Customer added!");
    }

    private static void listProducts() throws Exception {
        System.out.println("\n--- Products ---");
        List<Product> list = productService.getAll();
        for (Product p : list) {
            System.out.println(p);
        }
    }

    private static void addProduct(Scanner sc) throws Exception {
        System.out.println("\nAdd Product:");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(sc.nextLine());

        productService.addProduct(name, price);
        System.out.println("Product added!");
    }

    private static void recomputeRFM() throws Exception {
        System.out.println("\nRecomputing segments...");
        rfmService.recompute();
        System.out.println("RFM segmentation updated!");
    }
}