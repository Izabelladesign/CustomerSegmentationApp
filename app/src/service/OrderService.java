package service;

import dao.OrderDAO;
import model.Order;

import java.util.List;

public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();

    /**
     * Gets all orders for a specific customer.
     * Note: Order creation is handled by customers through their interface, not by admins.
     */
    public List<Order> getOrdersForCustomer(int customerID) throws Exception {
        return orderDAO.listByCustomer(customerID);
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
}
