package model;

public class Product {

    private int productID;
    private String productName;
    private double productPrice;  // Correct field name

    public Product(int productID, String productName, double productPrice) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
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

    @Override
    public String toString() {
        return productID + " - " + productName + " ($" + productPrice + ")";
    }

}
