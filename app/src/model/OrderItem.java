package model;

public class OrderItem {
    private int orderItemID;
    private int orderID;
    private int productID;
    private int quantity;
    private double unitPrice;
    private double lineTotal;

    public OrderItem(int orderItemID, int orderID, int productID,
                     int quantity, double unitPrice, double lineTotal) {
        this.orderItemID = orderItemID;
        this.orderID = orderID;
        this.productID = productID;
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

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getLineTotal() {
        return lineTotal;
    }

    @Override
    public String toString() {
        return "Item #" + orderItemID +
                " (Order " + orderID +
                ", Product " + productID +
                ", Qty=" + quantity +
                ", UnitPrice=$" + unitPrice +
                ", LineTotal=$" + lineTotal + ")";
    }
}
