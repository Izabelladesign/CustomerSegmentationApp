package model;

public class Segment {
    private int segmentID;
    private String segmentName;
    private String description;

    public Segment(int segmentID, String segmentName, String description) {
        this.segmentID = segmentID;
        this.segmentName = segmentName;
        this.description = description;
    }

    public int getSegmentID() {
        return segmentID;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return segmentID + " - " + segmentName + " (" + description + ")";
    }
}
