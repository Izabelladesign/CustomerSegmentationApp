package model;

public class Product {

    private int productID;
    private String productName;
    private double productPrice;  // Correct field name
    private int inventory;  // NOT NULL with default 0

    public Product(int productID, String productName, double productPrice) {
        this(productID, productName, productPrice, 0);
    }

    public Product(int productID, String productName, double productPrice, int inventory) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.inventory = inventory;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    // correct getter
    public double getProductPrice() {  
        return productPrice;
    }

    // needed for ProductDAO.insert()
    public double getUnitPrice() {
        return productPrice;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return productID + " - " + productName + " ($" + productPrice + ") [Stock: " + inventory + "]";
    }

}
