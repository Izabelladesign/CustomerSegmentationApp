package model;

import java.sql.Date;

/**
 * Represents a customer's segment data using RFM analysis.
 * Stores customer ID, segment ID, RFM values, score, and the reference date.
 */
public class CustomerSegment {
    private int id;
    private int customerID;
    private int segmentID;
    private Date asOfDate;
    private int r;
    private int f;
    private double m;
    private int rfmScore;

    /**
     * Constructor to create a CustomerSegment object with all attributes.
     */
    public CustomerSegment(int id, int customerID, int segmentID,
                           Date asOfDate, int r, int f, double m, int rfmScore) {
        this.id = id;
        this.customerID = customerID;
        this.segmentID = segmentID;
        this.asOfDate = asOfDate;
        this.r = r;
        this.f = f;
        this.m = m;
        this.rfmScore = rfmScore;
    }

    //getters 
    public int getId() {
        return id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getSegmentID() {
        return segmentID;
    }

    public Date getAsOfDate() {
        return asOfDate;
    }

    public int getR() {
        return r;
    }

    public int getF() {
        return f;
    }

    public double getM() {
        return m;
    }

    public int getRfmScore() {
        return rfmScore;
    }

    //returns a strong representation of the CustomerSegment 
    @Override
    public String toString() {
        return "CustomerSegment{" +
                "id=" + id +
                ", customerID=" + customerID +
                ", segmentID=" + segmentID +
                ", asOfDate=" + asOfDate +
                ", R=" + r +
                ", F=" + f +
                ", M=" + m +
                ", RFMScore=" + rfmScore +
                '}';
    }
}
