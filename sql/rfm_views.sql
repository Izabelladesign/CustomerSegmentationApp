USE segdb;

DROP VIEW IF EXISTS v_customer_metrics;

CREATE VIEW v_customer_metrics AS
SELECT
    c.CustomerID,
    DATEDIFF(CURRENT_DATE, MAX(o.OrderDate)) AS Recency,
    COUNT(o.OrderID) AS Frequency,
    SUM(o.OrderAmount) AS Monetary
FROM Customers c
LEFT JOIN Orders o ON c.CustomerID = o.CustomerID
GROUP BY c.CustomerID;

