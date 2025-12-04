package service;

import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.ProductDAO;
import model.Order;
import model.OrderWithCustomer;
import model.Product;

import java.util.List;

public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();

    public List<Order> getOrdersForCustomer(int customerID) throws Exception {
        return orderDAO.listByCustomer(customerID);
    }

    public int createOrder(int customerID, double amount) throws Exception {
        if (customerID <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Order amount must be positive.");
        }
        return orderDAO.insertOrder(customerID, amount);
    }

    public void updateOrderAmount(int orderID, double newAmount) throws Exception {
        if (newAmount < 0) {
            throw new IllegalArgumentException("Order amount cannot be negative.");
        }
        orderDAO.updateOrderAmount(orderID, newAmount);
    }

    public void deleteOrder(int orderID) throws Exception {
        orderDAO.deleteOrder(orderID);
    }

    public List<OrderWithCustomer> getAllOrdersWithCustomer() throws Exception {
        return orderDAO.listAllWithCustomer();
    }

    public List<OrderWithCustomer> getOrdersForCustomerWithName(int customerID) throws Exception {
        if (customerID <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive.");
        }
        return orderDAO.listByCustomerWithName(customerID);
    }

    /**
     * Creates an order with a product. Calculates order amount from product price and quantity.
     */
    public int createOrderWithProduct(int customerID, int productID, int quantity) throws Exception {
        if (customerID <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive.");
        }
        if (productID <= 0) {
            throw new IllegalArgumentException("Product ID must be positive.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        // Get product to get its price
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.findById(productID);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productID + " not found.");
        }

        // Calculate order amount
        double orderAmount = product.getProductPrice() * quantity;

        // Create the order
        int orderID = orderDAO.insertOrder(customerID, orderAmount);

        // Create the order item
        OrderItemDAO itemDAO = new OrderItemDAO();
        itemDAO.insertItem(orderID, productID, quantity, product.getProductPrice());

        return orderID;
    }
}
