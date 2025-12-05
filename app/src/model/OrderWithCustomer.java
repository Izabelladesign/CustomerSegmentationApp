package model;

import java.sql.Timestamp;

/**
 * DTO for Order with customer information included.
 */
public class OrderWithCustomer {
    private int orderID;
    private int customerID;
    private String customerName;
    private Timestamp orderDate;
    private double orderAmount;
    private String productInfo; // Format: "ProductID: ProductName (Qty: X), ..."

    public OrderWithCustomer(int orderID, int customerID, String customerName, Timestamp orderDate, double orderAmount) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.orderAmount = orderAmount;
        this.productInfo = "";
    }

    public OrderWithCustomer(int orderID, int customerID, String customerName, Timestamp orderDate, double orderAmount, String productInfo) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.orderAmount = orderAmount;
        this.productInfo = productInfo != null ? productInfo : "";
    }

    public int getOrderID() {
        return orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo != null ? productInfo : "";
    }
}

