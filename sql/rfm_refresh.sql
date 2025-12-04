USE segdb;


TRUNCATE TABLE CustomerSegments;

INSERT INTO CustomerSegments (CustomerID, SegmentID, AsOfDate, R, F, M, RFMScore)
SELECT
    q.CustomerID,

    -- Segment mapping based on RFM quintiles
    CASE
        WHEN q.R_Quintile >= 4 AND q.F_Quintile >= 4 AND q.M_Quintile >= 4 THEN 5  -- High-Value (555, 554, 545, etc.)
        WHEN q.R_Quintile >= 4 AND q.F_Quintile >= 3 THEN 1  -- Loyal (recent + frequent)
        WHEN q.R_Quintile >= 3 AND q.F_Quintile >= 1 THEN 2  -- New/Returning
        WHEN q.R_Quintile BETWEEN 2 AND 3 THEN 3  -- At-Risk
        ELSE 4  -- Churned
    END AS SegmentID,

    CURRENT_DATE,
    q.Recency,
    q.Frequency,
    q.Monetary,

    -- Industry-standard RFM score: 1-5 quintiles, concatenated as string
    -- 5 = best, 1 = worst
    -- Recency: Lower days = better, so reverse NTILE (5 = most recent, 1 = least recent)
    -- Frequency: Higher count = better (5 = most frequent, 1 = least frequent)
    -- Monetary: Higher amount = better (5 = highest, 1 = lowest)
    CONCAT(q.R_Quintile, q.F_Quintile, q.M_Quintile) AS RFMScore

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
