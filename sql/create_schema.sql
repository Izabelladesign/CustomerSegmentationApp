-- Create tables for Customer Segmentation Database
USE segdb;
SET FOREIGN_KEY_CHECKS = 0;

-- Drop tables in dependency order
DROP TABLE IF EXISTS OrderItems;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS CustomerSegments;
DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS Customers;
DROP TABLE IF EXISTS Segments;

-- 1. CUSTOMERS

CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    CustomerEmail VARCHAR(100) NOT NULL UNIQUE,
    CustomerStatus VARCHAR(20) NOT NULL,
    CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);



-- 2. PRODUCTS
CREATE TABLE Products (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(100) NOT NULL,
    ProductPrice DECIMAL(10,2) NOT NULL,
    Inventory INT NOT NULL DEFAULT 0
);



-- 3. ORDERS
CREATE TABLE Orders (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT NOT NULL,
    OrderDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- OrderAmount: Denormalized for performance. Should equal SUM(OrderItems.LineTotal) for the order.
    -- Used in RFM calculations (Monetary = SUM(OrderAmount)). Application code must keep this in sync.
    OrderAmount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
        ON DELETE CASCADE
);



-- 4. ORDER ITEMS
-- 4. ORDER ITEMS
CREATE TABLE OrderItems (
    OrderItemID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL CHECK (Quantity > 0),
    UnitPrice DECIMAL(10,2) NOT NULL,
    LineTotal DECIMAL(10,2) GENERATED ALWAYS AS (Quantity * UnitPrice) STORED NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
        ON DELETE CASCADE,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
        ON DELETE CASCADE        
);


-- 5. SEGMENTS
CREATE TABLE Segments (
    SegmentID INT AUTO_INCREMENT PRIMARY KEY,
    SegmentName VARCHAR(50) NOT NULL,
    Description VARCHAR(255) NOT NULL
);


-- 6. CUSTOMER SEGMENTS SNAPSHOT TABLE
-- Stores RFM analysis results as snapshots over time (allows tracking segment changes)
CREATE TABLE CustomerSegments (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT NOT NULL,
    SegmentID INT NOT NULL,
    AsOfDate DATE NOT NULL DEFAULT (CURRENT_DATE),  -- When this segmentation was calculated
    R INT NOT NULL,                                  -- Raw Recency value (days since last order)
    F INT NOT NULL,                                  -- Raw Frequency value (total order count)
    M DECIMAL(10,2) NOT NULL,                        -- Raw Monetary value (total amount spent)
    RFMScore TINYINT NOT NULL,                       -- Simplified overall score 1 (low) - 5 (high)
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
        ON DELETE CASCADE,
    FOREIGN KEY (SegmentID) REFERENCES Segments(SegmentID)
);

SET FOREIGN_KEY_CHECKS = 1;