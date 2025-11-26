package model;

public class Product {
    private int productID;
    private String productName;
    private double unitPrice;

    public Product(int productID, String productName, double unitPrice) {
        this.productID = productID;
        this.productName = productName;
        this.unitPrice = unitPrice;
    }

    // getters only (we don't modify product records)
    public int getProductID() { return productID; }
    public String getProductName() { return productName; }
    public double getUnitPrice() { return unitPrice; }
}