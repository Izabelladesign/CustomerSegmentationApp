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

-- Insert orders with varied dates to ensure each segment has customers

INSERT INTO Orders (CustomerID, OrderDate, OrderAmount)
VALUES
-- High-Value customers: High spending, recent, frequent (Segment 5)
(1, DATE_SUB(CURRENT_DATE, INTERVAL 10 DAY), 1200.00),  -- High spending, recent
(1, DATE_SUB(CURRENT_DATE, INTERVAL 25 DAY), 800.00),   -- Multiple orders
(1, DATE_SUB(CURRENT_DATE, INTERVAL 40 DAY), 600.00),
(6, DATE_SUB(CURRENT_DATE, INTERVAL 15 DAY), 1500.00),  -- Very high spending
(6, DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY), 900.00),

-- Loyal customers: Recent + frequent (Segment 1)
(2, DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY), 200.00),    -- Very recent
(2, DATE_SUB(CURRENT_DATE, INTERVAL 20 DAY), 150.00),   -- Multiple orders
(2, DATE_SUB(CURRENT_DATE, INTERVAL 35 DAY), 100.00),
(4, DATE_SUB(CURRENT_DATE, INTERVAL 8 DAY), 300.00),    -- Recent
(4, DATE_SUB(CURRENT_DATE, INTERVAL 22 DAY), 250.00),   -- Multiple orders

-- New customers: Recent but lower frequency (Segment 2)
(3, DATE_SUB(CURRENT_DATE, INTERVAL 12 DAY), 150.00),   -- Recent, single order
(5, DATE_SUB(CURRENT_DATE, INTERVAL 18 DAY), 180.00),    -- Recent, single order
(7, DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY), 120.00),   -- Very recent, single order

-- At-Risk customers: Medium recency, declining activity (Segment 3)
(8, DATE_SUB(CURRENT_DATE, INTERVAL 100 DAY), 200.00),   -- 100 days ago
(8, DATE_SUB(CURRENT_DATE, INTERVAL 120 DAY), 150.00),   -- Last order 120 days
(9, DATE_SUB(CURRENT_DATE, INTERVAL 95 DAY), 300.00),     -- 95 days ago
(10, DATE_SUB(CURRENT_DATE, INTERVAL 110 DAY), 250.00),  -- 110 days ago

-- Churned customers: Old orders, no recent activity (Segment 4)
(11, DATE_SUB(CURRENT_DATE, INTERVAL 250 DAY), 100.00), -- 250 days ago
(12, DATE_SUB(CURRENT_DATE, INTERVAL 280 DAY), 200.00),  -- 280 days ago
(13, DATE_SUB(CURRENT_DATE, INTERVAL 300 DAY), 150.00),  -- 300 days ago
(14, DATE_SUB(CURRENT_DATE, INTERVAL 320 DAY), 180.00), -- 320 days ago
(15, DATE_SUB(CURRENT_DATE, INTERVAL 350 DAY), 120.00);  -- 350 days ago

-- Insert order items (updated to match new orders - 22 orders total)
INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitPrice)
VALUES
-- Customer 1 orders (High-Value: 3 orders)
(1, 1, 1, 899.99),   -- Laptop
(1, 7, 1, 199.99),   -- Headphones
(1, 12, 1, 149.99),  -- SSD
(2, 1, 1, 899.99),   -- Laptop
(2, 2, 1, 29.99),    -- Mouse
(3, 13, 1, 299.99),  -- Office Chair
(3, 8, 1, 249.99),  -- Smartwatch

-- Customer 2 orders (Loyal: 3 orders)
(4, 7, 1, 199.99),   -- Headphones
(5, 6, 1, 79.99),    -- Speaker
(5, 3, 1, 14.99),    -- Phone Case
(6, 8, 1, 249.99),   -- Smartwatch
(6, 5, 1, 9.99),     -- USB-C Cable

-- Customer 3 order (New: 1 order)
(7, 10, 1, 129.99),  -- Gaming Keyboard
(7, 11, 1, 59.99),   -- Monitor Stand

-- Customer 4 orders (Loyal: 2 orders)
(8, 12, 1, 149.99),  -- SSD
(8, 6, 1, 79.99),    -- Speaker
(9, 7, 1, 199.99),   -- Headphones
(9, 4, 1, 49.99),    -- Desk Lamp

-- Customer 5 order (New: 1 order)
(10, 8, 1, 249.99),  -- Smartwatch
(10, 3, 1, 14.99),   -- Phone Case

-- Customer 6 orders (High-Value: 2 orders)
(11, 1, 1, 899.99),  -- Laptop
(11, 13, 1, 299.99), -- Office Chair
(11, 7, 1, 199.99),  -- Headphones
(12, 1, 1, 899.99),  -- Laptop
(12, 12, 1, 149.99), -- SSD

-- Customer 7 order (New: 1 order)
(13, 6, 1, 79.99),   -- Speaker
(13, 9, 1, 39.99),   -- Portable Charger

-- Customer 8 orders (At-Risk: 2 orders)
(14, 10, 1, 129.99), -- Gaming Keyboard
(14, 11, 1, 59.99),  -- Monitor Stand
(15, 10, 1, 129.99), -- Gaming Keyboard

-- Customer 9 order (At-Risk: 1 order)
(16, 13, 1, 299.99), -- Office Chair

-- Customer 10 order (At-Risk: 1 order)
(17, 12, 1, 149.99), -- SSD
(17, 8, 1, 249.99),  -- Smartwatch

-- Customer 11 order (Churned: 1 order)
(18, 3, 1, 14.99),   -- Phone Case
(18, 5, 1, 9.99),    -- USB-C Cable

-- Customer 12 order (Churned: 1 order)
(19, 14, 1, 219.99), -- Graphic Tablet
(19, 6, 1, 79.99),   -- Speaker

-- Customer 13 order (Churned: 1 order)
(20, 1, 1, 899.99),  -- Laptop

-- Customer 14 order (Churned: 1 order)
(21, 7, 1, 199.99),  -- Headphones
(21, 9, 1, 39.99),   -- Portable Charger

-- Customer 15 order (Churned: 1 order)
(22, 15, 1, 89.99),  -- Webcam
(22, 11, 1, 59.99);  -- Monitor Stand

-- Insert segments (5 core segments)
INSERT INTO Segments (SegmentID, SegmentName, Description) VALUES
(1, 'Loyal', 'Frequent and recent purchasers with strong activity'),
(2, 'New', 'Recently joined customers'),
(3, 'At-Risk', 'Customers slowing their activity'),
(4, 'Churned', 'Customers inactive for a long time'),
(5, 'High-Value', 'Customers with high spending');

