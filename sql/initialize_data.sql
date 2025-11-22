USE segdb;

-- Insert sample customers
INSERT INTO Customers (FirstName, LastName, CustomerEmail, CustomerStatus)
VALUES
('Zara', 'Rahim', 'Zara@example.com', 'Active'),
('Arianna', 'Gonzalez', 'Arianna@example.com', 'Active'),
('Maseena', 'Usman', 'Maseena@example.com', 'Inactive'),
('Yemimah', 'Raza', 'Yemimah@example.com', 'Active'),
('Aiyanna', 'Muniz', 'Aiyanna@example.com', 'Active');

-- Insert sample products
INSERT INTO Products (ProductName, ProductPrice)
VALUES
('Laptop', 899.99),
('Wireless Mouse', 29.99),
('Phone Case', 14.99),
('Desk Lamp', 49.99),
('USB-C Cable', 9.99);


-- Insert sample orders

-- Zara orders
INSERT INTO Orders (CustomerID, OrderDate, OrderAmount)
VALUES (1, '2025-01-15', 944.97);

-- Arianna orders
INSERT INTO Orders (CustomerID, OrderDate, OrderAmount)
VALUES (2, '2025-02-03', 29.99);

-- Maseena orders
INSERT INTO Orders (CustomerID, OrderDate, OrderAmount)
VALUES (3, '2025-01-20', 49.99);


-- Insert sample order items 

-- Zara's big order (OrderID 1)
INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitPrice)
VALUES 
(1, 1, 1, 899.99),    -- Laptop
(1, 2, 1, 29.99),     -- Wireless Mouse
(1, 5, 1, 14.99);     -- USB-C Cable

-- Arianna's simple order
INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitPrice)
VALUES (2, 2, 1, 29.99);

-- Maseena's order
INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitPrice)
VALUES (3, 4, 1, 49.99);