USE segdb;

DROP VIEW IF EXISTS v_customer_metrics;

CREATE VIEW v_customer_metrics AS
SELECT
    c.CustomerID,
    COALESCE(DATEDIFF(CURRENT_DATE, MAX(o.OrderDate)), 999) AS Recency,
    COALESCE(COUNT(o.OrderID), 0) AS Frequency,
    COALESCE(SUM(o.OrderAmount), 0) AS Monetary
FROM Customers c
LEFT JOIN Orders o ON c.CustomerID = o.CustomerID
GROUP BY c.CustomerID;

