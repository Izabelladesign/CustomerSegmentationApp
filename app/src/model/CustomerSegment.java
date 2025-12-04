package model;

import java.sql.Date;

public class CustomerSegment {
    private int id;
    private int customerID;
    private int segmentID;
    private Date asOfDate;
    private int r;
    private int f;
    private double m;
    private int rfmScore;

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
