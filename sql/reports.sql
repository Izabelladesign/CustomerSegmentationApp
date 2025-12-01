USE segdb;

-- 1) Revenue + customer count per segment
DROP VIEW IF EXISTS v_revenue_by_segment;

CREATE VIEW v_revenue_by_segment AS
SELECT
    s.SegmentID,
    s.SegmentName,
    COUNT(cs.CustomerID)              AS CustomerCount,
    COALESCE(SUM(cs.M), 0)            AS TotalRevenue
FROM Segments s
LEFT JOIN CustomerSegments cs
       ON s.SegmentID = cs.SegmentID
GROUP BY s.SegmentID, s.SegmentName
ORDER BY s.SegmentID;

-- 2) Top 5 customers by total spending
DROP VIEW IF EXISTS v_top_customers;

CREATE VIEW v_top_customers AS
SELECT
    c.CustomerID,
    CONCAT(c.FirstName, ' ', c.LastName) AS CustomerName,
    COALESCE(SUM(o.OrderAmount), 0)      AS TotalSpent
FROM Customers c
LEFT JOIN Orders o
       ON c.CustomerID = o.CustomerID
GROUP BY c.CustomerID, CustomerName
ORDER BY TotalSpent DESC
LIMIT 5;

-- 3) Customers with no orders at all
DROP VIEW IF EXISTS v_customers_without_orders;

CREATE VIEW v_customers_without_orders AS
SELECT
    c.*
FROM Customers c
LEFT JOIN Orders o
       ON c.CustomerID = o.CustomerID
WHERE o.CustomerID IS NULL;
