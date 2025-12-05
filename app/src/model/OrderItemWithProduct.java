package model;

/**
 * DTO for OrderItem with product information included.
 */
public class OrderItemWithProduct {
    private int orderItemID;
    private int orderID;
    private int productID;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double lineTotal;

    public OrderItemWithProduct(int orderItemID, int orderID, int productID, 
                                String productName, int quantity, double unitPrice, double lineTotal) {
        this.orderItemID = orderItemID;
        this.orderID = orderID;
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    public int getOrderItemID() {
        return orderItemID;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getLineTotal() {
        return lineTotal;
    }
}

