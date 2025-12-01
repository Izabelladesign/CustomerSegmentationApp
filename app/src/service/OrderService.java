package service;

import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.ProductDAO;
import model.Order;
import model.Product;

import java.util.List;

public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();
    private final ProductDAO productDAO = new ProductDAO();

    /**
     * Simplest order: one customer, one product, one quantity.
     */
    public int createSimpleOrder(int customerID, int productID, int quantity) throws Exception {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        Product p = productDAO.findById(productID);
        if (p == null) {
            throw new IllegalArgumentException("No product with ID " + productID);
        }

        double unitPrice = p.getProductPrice();
        double orderAmount = unitPrice * quantity;

        // Insert order header
        int orderID = orderDAO.insertOrder(customerID, orderAmount);

        // Insert item row
        orderItemDAO.insertItem(orderID, productID, quantity, unitPrice);

        return orderID;
    }

    public List<Order> getOrdersForCustomer(int customerID) throws Exception {
        return orderDAO.listByCustomer(customerID);
    }
}
