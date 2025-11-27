USE segdb;

DELETE FROM CustomerSegments;

INSERT INTO CustomerSegments (CustomerID, SegmentID, AsOfDate, R, F, M, RFMScore)
SELECT
    m.CustomerID,

    #segment mapping 
    CASE
        WHEN m.Recency < 60 AND m.Frequency >= 3 AND m.Monetary > 500 THEN 5  
        WHEN m.Recency < 60 AND m.Frequency >= 3                                THEN 1  
        WHEN m.Recency < 90 AND m.Frequency >= 1                                THEN 2 
        WHEN m.Recency BETWEEN 90 AND 180                                       THEN 3  
        ELSE 4                                                                 
    END AS SegmentID,

    CURRENT_DATE,
    m.Recency,
    m.Frequency,
    m.Monetary,

    -- RFM score
    (
        (CASE WHEN m.Recency < 60 THEN 1 WHEN m.Recency < 120 THEN 2 ELSE 3 END) * 100 +
        (CASE WHEN m.Frequency >= 3 THEN 1 WHEN m.Frequency >= 2 THEN 2 ELSE 3 END) * 10 +
        (CASE WHEN m.Monetary > 300 THEN 1 WHEN m.Monetary > 100 THEN 2 ELSE 3 END)
    ) AS RFMScore

FROM v_customer_metrics m;
