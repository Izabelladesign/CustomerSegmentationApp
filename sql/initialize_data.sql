USE segdb;

-- Insert customers (15 records)
INSERT INTO Customers (FirstName, LastName, CustomerEmail, CustomerStatus)
VALUES
('Zara', 'Rahim', 'zara@example.com', 'Active'),
('Arianna', 'Grande', 'arianna@example.com', 'Active'),
('Maseena', 'Usman', 'maseena@example.com', 'Inactive'),
('Yemimah', 'Raza', 'yemimah@example.com', 'Active'),
('Aiyanna', 'Muniz', 'aiyanna@example.com', 'Active'),
('Jonathan', 'Bailey', 'jonathan.bailey@example.com', 'Active'),
('Carlos', 'Sainz', 'carlos.sainz@example.com', 'Active'),
('Charles', 'LeClerc', 'charles.leclerc@example.com', 'Active'),
('Alex', 'Albon', 'alex.albon@example.com', 'Inactive'),
('Lewis', 'Hamilton', 'lewis.hamilton@example.com', 'Active'),
('AJ', 'Doser', 'aj.doser@example.com', 'Active'),
('John', 'Doe', 'john.doe@example.com', 'Inactive'),
('Zayn', 'Malik', 'zayn.malik@example.com', 'Active'),
('Bella', 'Sawn', 'bella.sawn@example.com', 'Inactive'),
('Edward', 'Cullen', 'edward.cullen@example.com', 'Active');

-- Insert products (15 records)
INSERT INTO Products (ProductName, ProductPrice)
VALUES
('Laptop', 899.99),
('Wireless Mouse', 29.99),
('Phone Case', 14.99),
('Desk Lamp', 49.99),
('USB-C Cable', 9.99),
('Bluetooth Speaker', 79.99),
('Noise Cancelling Headphones', 199.99),
('Smartwatch', 249.99),
('Portable Charger', 39.99),
('Gaming Keyboard', 129.99),
('Monitor Stand', 59.99),
('External SSD', 149.99),
('Office Chair', 299.99),
('Graphic Tablet', 219.99),
('Webcam', 89.99);

-- Insert orders (15 records)
INSERT INTO Orders (CustomerID, OrderDate, OrderAmount)
VALUES
(1, '2025-01-15', 939.97),
(2, '2025-02-03', 29.99),
(3, '2025-01-20', 49.99),
(4, '2025-03-05', 264.98),
(5, '2025-02-10', 79.99),
(6, '2025-02-14', 1099.98),
(7, '2025-03-01', 49.98),
(8, '2025-01-25', 189.98),
(9, '2025-02-18', 299.99),
(10, '2025-03-12', 399.98),
(11, '2025-01-30', 24.98),
(12, '2025-02-22', 299.98),
(13, '2025-03-03', 899.99),
(14, '2025-03-08', 239.98),
(15, '2025-03-15', 149.98);

-- Insert order items (20 records, at least one per order)
INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitPrice)
VALUES
(1, 1, 1, 899.99),
(1, 2, 1, 29.99),
(1, 5, 1, 9.99),
(2, 2, 1, 29.99),
(3, 4, 1, 49.99),
(4, 8, 1, 249.99),a
(4, 3, 1, 14.99),
(5, 6, 1, 79.99),
(6, 1, 1, 899.99),
(6, 7, 1, 199.99),
(7, 9, 1, 39.99),
(7, 5, 1, 9.99),
(8, 10, 1, 129.99),
(8, 11, 1, 59.99),
(9, 13, 1, 299.99),
(10, 12, 1, 149.99),
(10, 8, 1, 249.99),
(11, 3, 1, 14.99),
(11, 5, 1, 9.99),
(12, 14, 1, 219.99),
(12, 6, 1, 79.99),
(13, 1, 1, 899.99),
(14, 7, 1, 199.99),
(14, 9, 1, 39.99),
(15, 15, 1, 89.99),
(15, 11, 1, 59.99);

-- Insert segments (15 records)
INSERT INTO Segments (SegmentID, SegmentName, Description) VALUES
(1, 'Loyal', 'Frequent and recent purchasers with strong activity'),
(2, 'New', 'Recently joined customers'),
(3, 'At-Risk', 'Customers slowing their activity'),
(4, 'Churned', 'Customers inactive for a long time'),
(5, 'High-Value', 'Customers with high spending'),
(6, 'Seasonal', 'Customers who purchase around seasonal events'),
(7, 'VIP', 'Top spenders with personalized attention'),
(8, 'Returning', 'Customers who reactivated after a break'),
(9, 'Prospect', 'Leads yet to make large purchases'),
(10, 'Budget', 'Customers focused on low-cost items'),
(11, 'Premium', 'Customers who prefer premium SKUs'),
(12, 'Dormant', 'Customers idle for extended periods'),
(13, 'Advocate', 'Promoters who refer others'),
(14, 'Referred', 'Customers gained through referrals'),
(15, 'Upsell', 'Customers targeted for cross-sell and upsell');

-- Insert customer segments snapshot (15 records)
INSERT INTO CustomerSegments (CustomerID, SegmentID, AsOfDate, R, F, M, RFMScore)
VALUES
(1, 5, '2025-03-31', 15, 6, 1520.50, 111),
(2, 2, '2025-03-31', 10, 2, 120.45, 221),
(3, 3, '2025-03-31', 95, 1, 49.99, 332),
(4, 1, '2025-03-31', 25, 4, 540.96, 123),
(5, 8, '2025-03-31', 40, 3, 230.97, 223),
(6, 7, '2025-03-31', 12, 5, 1800.40, 112),
(7, 10, '2025-03-31', 55, 2, 89.97, 233),
(8, 1, '2025-03-31', 18, 4, 760.15, 122),
(9, 12, '2025-03-31', 180, 1, 299.99, 433),
(10, 5, '2025-03-31', 22, 5, 999.50, 121),
(11, 9, '2025-03-31', 8, 1, 24.98, 311),
(12, 6, '2025-03-31', 35, 3, 450.25, 222),
(13, 13, '2025-03-31', 5, 7, 2100.00, 111),
(14, 4, '2025-03-31', 200, 1, 239.98, 443),
(15, 14, '2025-03-31', 60, 2, 310.75, 233);
