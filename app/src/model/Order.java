package model;

import java.sql.Timestamp;

public class Order {
    private int orderID;
    private int customerID;
    private Timestamp orderDate;
    private double orderAmount;

    public Order(int orderID, int customerID, Timestamp orderDate, double orderAmount) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.orderAmount = orderAmount;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    @Override
    public String toString() {
        return "Order #" + orderID +
                " (Customer " + customerID + ", Date=" + orderDate +
                ", Amount=$" + orderAmount + ")";
    }
}
