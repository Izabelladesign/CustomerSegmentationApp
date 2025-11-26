USE segdb;

DELETE FROM CustomerSegments;

INSERT INTO CustomerSegments (CustomerID, SegmentID, AsOfDate, R, F, M, RFMScore)
SELECT
    m.CustomerID,
    CASE
        WHEN m.Recency < 60 AND m.Frequency >= 3 AND m.Monetary > 300 THEN 1
        WHEN m.Recency < 120 AND m.Frequency >= 2 THEN 2
        ELSE 3
    END AS SegmentID,
    CURRENT_DATE,
    m.Recency,
    m.Frequency,
    m.Monetary,
    CONCAT(
        CASE WHEN m.Recency < 60 THEN 1 WHEN m.Recency < 120 THEN 2 ELSE 3 END,
        CASE WHEN m.Frequency >= 3 THEN 1 WHEN m.Frequency >= 2 THEN 2 ELSE 3 END,
        CASE WHEN m.Monetary > 300 THEN 1 WHEN m.Monetary > 100 THEN 2 ELSE 3 END
    ) AS RFMScore
FROM v_customer_metrics m;