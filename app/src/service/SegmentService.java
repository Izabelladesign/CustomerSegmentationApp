package service;

import dao.SegmentDAO;
import model.Segment;
import java.util.List;

public class SegmentService {

    private final SegmentDAO segmentDAO = new SegmentDAO();

    public List<Segment> getAll() throws Exception {
        return segmentDAO.listAll();
    }

    public void addSegment(String segmentName, String description) throws Exception {
        if (segmentName == null || segmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Segment name cannot be empty.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        segmentDAO.insert(segmentName.trim(), description.trim());
    }

    public void updateSegment(int segmentID, String segmentName, String description) throws Exception {
        if (segmentID <= 0) {
            throw new IllegalArgumentException("Segment ID must be positive.");
        }
        if (segmentName == null || segmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Segment name cannot be empty.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        segmentDAO.update(segmentID, segmentName.trim(), description.trim());
    }

    public void deleteSegment(int segmentID) throws Exception {
        if (segmentID <= 0) {
            throw new IllegalArgumentException("Segment ID must be positive.");
        }
        segmentDAO.delete(segmentID);
    }
}

