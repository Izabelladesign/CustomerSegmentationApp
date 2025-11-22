-- Create tables for Customer Segmentation Database
USE segdb;

-- 1. CUSTOMERS
DROP TABLE IF EXISTS Customers;

CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    CustomerEmail VARCHAR(100) UNIQUE,
    CustomerStatus VARCHAR(20),
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP
);



-- 2. PRODUCTS
DROP TABLE IF EXISTS Products;

CREATE TABLE Products (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(100),
    ProductPrice DECIMAL(10,2)
);



-- 3. ORDERS
DROP TABLE IF EXISTS Orders;

CREATE TABLE Orders (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT,
    OrderDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    OrderAmount DECIMAL(10,2),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
        ON DELETE CASCADE
);


-- 4. ORDER ITEMS
DROP TABLE IF EXISTS OrderItems;

CREATE TABLE OrderItems (
    OrderItemID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID INT,
    ProductID INT,
    Quantity INT CHECK (Quantity > 0),
    UnitPrice DECIMAL(10,2),
    LineTotal DECIMAL(10,2) GENERATED ALWAYS AS (Quantity * UnitPrice) STORED,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
        ON DELETE CASCADE,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);


-- 5. SEGMENTS
DROP TABLE IF EXISTS Segments;

CREATE TABLE Segments (
    SegmentID INT AUTO_INCREMENT PRIMARY KEY,
    SegmentName VARCHAR(50),
    Description VARCHAR(255)
);


-- 6. CUSTOMER SEGMENTS SNAPSHOT TABLE
DROP TABLE IF EXISTS CustomerSegments;

CREATE TABLE CustomerSegments (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT,
    SegmentID INT,
    AsOfDate DATE DEFAULT (CURRENT_DATE),
    R INT,
    F INT,
    M DECIMAL(10,2),
    RFMScore INT,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
        ON DELETE CASCADE,
    FOREIGN KEY (SegmentID) REFERENCES Segments(SegmentID)
);