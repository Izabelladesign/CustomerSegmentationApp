USE segdb;

-- Insert customers (15 entries)
INSERT INTO Customers (FirstName, LastName, CustomerEmail, CustomerStatus)
VALUES
('Zara', 'Rahim', 'zara@gmail.com', 'Active'),
('Arianna', 'Grande', 'arianna@gmail.com', 'Active'),
('Maseena', 'Usman', 'maseena@gmail.com', 'Inactive'),
('Yemimah', 'Raza', 'yemimah@gmail.com', 'Active'),
('Aiyanna', 'Muniz', 'aiyanna@gmail.com', 'Active'),
('Jonathan', 'Bailey', 'jonathan.bailey@gmail.com', 'Active'),
('Carlos', 'Sainz', 'carlos.sainz@gmail.com', 'Active'),
('Charles', 'LeClerc', 'charles.leclerc@gmail.com', 'Active'),
('Alex', 'Albon', 'alex.albon@gmail.com', 'Inactive'),
('Lewis', 'Hamilton', 'lewis.hamilton@gmail.com', 'Active'),
('AJ', 'Doser', 'aj.doser@gmail.com', 'Active'),
('John', 'Doe', 'john.doe@gmail.com', 'Inactive'),
('Zayn', 'Malik', 'zayn.malik@gmail.com', 'Active'),
('Bella', 'Sawn', 'bella.sawn@gmail.com', 'Inactive'),
('Edward', 'Cullen', 'edward.cullen@gmail.com', 'Active');

-- Insert products (15 entries)
INSERT INTO Products (ProductName, ProductPrice, Inventory)
VALUES
('Laptop', 899.99, 10),
('Wireless Mouse', 29.99, 50),
('Phone Case', 14.99, 100),
('Desk Lamp', 49.99, 30),
('USB-C Cable', 9.99, 200),
('Bluetooth Speaker', 79.99, 25),
('Noise Cancelling Headphones', 199.99, 15),
('Smartwatch', 249.99, 20),
('Portable Charger', 39.99, 40),
('Gaming Keyboard', 129.99, 18),
('Monitor Stand', 59.99, 35),
('External SSD', 149.99, 22),
('Office Chair', 299.99, 12),
('Graphic Tablet', 219.99, 8),
('Webcam', 89.99, 28);

-- Insert orders with varied dates for segment diversity
-- Recent orders (0-60 days): Customers 1, 2, 3, 4, 5
-- Medium orders (60-180 days): Customers 6, 7, 8, 9, 10
-- Old orders (180+ days): Customers 11, 12, 13, 14, 15
-- Some customers have multiple orders for frequency variety

INSERT INTO Orders (CustomerID, OrderDate, OrderAmount)
VALUES
-- Recent, high-frequency customers (Loyal/High-Value segments)
(1, DATE_SUB(CURRENT_DATE, INTERVAL 15 DAY), 939.97),
(1, DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY), 199.99),
(1, DATE_SUB(CURRENT_DATE, INTERVAL 45 DAY), 149.99),
(2, DATE_SUB(CURRENT_DATE, INTERVAL 10 DAY), 29.99),
(2, DATE_SUB(CURRENT_DATE, INTERVAL 25 DAY), 79.99),
(3, DATE_SUB(CURRENT_DATE, INTERVAL 20 DAY), 49.99),
(3, DATE_SUB(CURRENT_DATE, INTERVAL 35 DAY), 99.99),
(4, DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY), 264.98),
(4, DATE_SUB(CURRENT_DATE, INTERVAL 40 DAY), 199.99),
(5, DATE_SUB(CURRENT_DATE, INTERVAL 12 DAY), 79.99),

-- Medium recency customers (At-Risk/Returning segments)
(6, DATE_SUB(CURRENT_DATE, INTERVAL 90 DAY), 1099.98),
(6, DATE_SUB(CURRENT_DATE, INTERVAL 100 DAY), 299.99),
(7, DATE_SUB(CURRENT_DATE, INTERVAL 75 DAY), 49.98),
(8, DATE_SUB(CURRENT_DATE, INTERVAL 110 DAY), 189.98),
(8, DATE_SUB(CURRENT_DATE, INTERVAL 120 DAY), 129.99),
(9, DATE_SUB(CURRENT_DATE, INTERVAL 95 DAY), 299.99),
(10, DATE_SUB(CURRENT_DATE, INTERVAL 85 DAY), 399.98),

-- Old orders (Churned segment)
(11, DATE_SUB(CURRENT_DATE, INTERVAL 200 DAY), 24.98),
(12, DATE_SUB(CURRENT_DATE, INTERVAL 250 DAY), 299.98),
(13, DATE_SUB(CURRENT_DATE, INTERVAL 220 DAY), 899.99),
(14, DATE_SUB(CURRENT_DATE, INTERVAL 280 DAY), 239.98),
(15, DATE_SUB(CURRENT_DATE, INTERVAL 300 DAY), 149.98);

-- Insert order items (updated to match new orders)
INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitPrice)
VALUES
-- Customer 1 orders (3 orders)
(1, 1, 1, 899.99),
(1, 2, 1, 29.99),
(1, 5, 1, 9.99),
(2, 7, 1, 199.99),
(3, 12, 1, 149.99),
-- Customer 2 orders (2 orders)
(4, 2, 1, 29.99),
(5, 6, 1, 79.99),
-- Customer 3 orders (2 orders)
(6, 4, 1, 49.99),
(7, 6, 1, 79.99),
(7, 3, 1, 14.99),
-- Customer 4 orders (2 orders)
(8, 8, 1, 249.99),
(8, 3, 1, 14.99),
(9, 7, 1, 199.99),
-- Customer 5 order
(10, 6, 1, 79.99),
-- Customer 6 orders (2 orders)
(11, 1, 1, 899.99),
(11, 7, 1, 199.99),
(12, 13, 1, 299.99),
-- Customer 7 order
(13, 9, 1, 39.99),
(13, 5, 1, 9.99),
-- Customer 8 orders (2 orders)
(14, 10, 1, 129.99),
(14, 11, 1, 59.99),
(15, 10, 1, 129.99),
-- Customer 9 order
(16, 13, 1, 299.99),
-- Customer 10 order
(17, 12, 1, 149.99),
(17, 8, 1, 249.99),
-- Customer 11 order (old)
(18, 3, 1, 14.99),
(18, 5, 1, 9.99),
-- Customer 12 order (old)
(19, 14, 1, 219.99),
(19, 6, 1, 79.99),
-- Customer 13 order (old)
(20, 1, 1, 899.99),
-- Customer 14 order (old)
(21, 7, 1, 199.99),
(21, 9, 1, 39.99),
-- Customer 15 order (old)
(22, 15, 1, 89.99),
(22, 11, 1, 59.99);

-- Insert segments (15 entries)
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

-- Note: CustomerSegments are now calculated dynamically via rfm_refresh.sql
-- Run "Recompute RFM Segments" in the GUI to populate this table
