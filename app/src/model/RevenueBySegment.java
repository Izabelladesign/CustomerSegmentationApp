package model;

public class RevenueBySegment {
    private String segmentName;
    private long customerCount;
    private double totalRevenue;

    public RevenueBySegment(String segmentName, long customerCount, double totalRevenue) {
        this.segmentName = segmentName;
        this.customerCount = customerCount;
        this.totalRevenue = totalRevenue;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public long getCustomerCount() {
        return customerCount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    @Override
    public String toString() {
        return segmentName + " - Customers: " + customerCount +
                ", Revenue: $" + totalRevenue;
    }
}
