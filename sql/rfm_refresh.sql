USE segdb;


TRUNCATE TABLE CustomerSegments;

INSERT INTO CustomerSegments (CustomerID, SegmentID, AsOfDate, R, F, M, RFMScore)
SELECT
    q.CustomerID,

    -- Segment mapping based on RFM quintiles (5 segments: 1=Loyal, 2=New, 3=At-Risk, 4=Churned, 5=High-Value)
    CASE
        WHEN q.R_Quintile >= 4 AND q.F_Quintile >= 4 AND q.M_Quintile >= 4 THEN 5 
        WHEN q.R_Quintile >= 4 AND q.F_Quintile >= 3 AND q.M_Quintile < 4 THEN 1  
        WHEN q.R_Quintile >= 4 AND q.F_Quintile < 3 THEN 2  
        WHEN q.R_Quintile BETWEEN 2 AND 3 THEN 3 
        ELSE 4  
    END AS SegmentID,

    CURRENT_DATE,
    q.Recency,
    q.Frequency,
    q.Monetary,

    -- Simplified overall RFM score (1 = lowest, 5 = highest) using rounded average of the three quintiles
    ROUND((q.R_Quintile + q.F_Quintile + q.M_Quintile) / 3, 0) AS RFMScore

FROM (
    SELECT
        m.CustomerID,
        m.Recency,
        m.Frequency,
        m.Monetary,
        (6 - NTILE(5) OVER (ORDER BY m.Recency ASC)) AS R_Quintile, 
        NTILE(5) OVER (ORDER BY m.Frequency DESC) AS F_Quintile,     
        NTILE(5) OVER (ORDER BY m.Monetary DESC) AS M_Quintile    
    FROM v_customer_metrics m
) q;
