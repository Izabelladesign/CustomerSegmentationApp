USE segdb;


TRUNCATE TABLE CustomerSegments;

INSERT INTO CustomerSegments (CustomerID, SegmentID, AsOfDate, R, F, M, RFMScore)
SELECT
    q.CustomerID,

    -- Segment mapping based on RFM quintiles (5 segments: 1=Loyal, 2=New, 3=At-Risk, 4=Churned, 5=High-Value)
    CASE
        WHEN q.R_Quintile >= 4 AND q.F_Quintile >= 4 AND q.M_Quintile >= 4 THEN 5  -- High-Value (high R, F, M)
        WHEN q.R_Quintile >= 4 AND q.F_Quintile >= 3 AND q.M_Quintile < 4 THEN 1  -- Loyal (recent + frequent, moderate spending)
        WHEN q.R_Quintile >= 4 AND q.F_Quintile < 3 THEN 2  -- New (recent but low frequency)
        WHEN q.R_Quintile BETWEEN 2 AND 3 THEN 3  -- At-Risk (medium recency)
        ELSE 4  -- Churned (low recency)
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
        (6 - NTILE(5) OVER (ORDER BY m.Recency ASC)) AS R_Quintile,  -- R: 5 = most recent, 1 = least recent
        NTILE(5) OVER (ORDER BY m.Frequency DESC) AS F_Quintile,     -- F: 5 = most frequent, 1 = least frequent
        NTILE(5) OVER (ORDER BY m.Monetary DESC) AS M_Quintile        -- M: 5 = highest spending, 1 = lowest spending
    FROM v_customer_metrics m
) q;
