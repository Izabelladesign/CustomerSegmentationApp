package service;

import dao.OrderDAO;
import model.Order;
import model.OrderWithCustomer;

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
}
