package project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import project.request.LeaveRequest;
import project.status.LeaveStatus;

@Service
public class LeaveRequestService {
    private final List<LeaveRequest> leaveRequests = new ArrayList<>();

    // Add a new leave request (Defaults to PENDING)
    public LeaveRequest requestLeave(LeaveRequest leaveRequest) {
        leaveRequest.setStatus(LeaveStatus.PENDING);
        leaveRequests.add(leaveRequest);
        return leaveRequest;
    }

    // Get all pending leave requests
    public List<LeaveRequest> getPendingRequests() {
        List<LeaveRequest> pendingRequests = new ArrayList<>();
        for (LeaveRequest request : leaveRequests) {
            if (request.getStatus() == LeaveStatus.PENDING) {
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }

    // Update leave request status
    public Optional<LeaveRequest> updateLeaveStatus(Long staffId, LeaveStatus status) {
        for (LeaveRequest request : leaveRequests) {
            if (request.getStaffId().equals(staffId)) {
                request.setStatus(status);
                return Optional.of(request);
            }
        }
        return Optional.empty();
    }

    // Get the number of approved leaves for a staff member
    public int getApprovedLeaveCount(Long staffId) {
        int count = 0;
        for (LeaveRequest request : leaveRequests) {
            if (request.getStaffId().equals(staffId) && request.getStatus() == LeaveStatus.APPROVED) {
                count++;
            }
        }
        return count;
    }

    // Get all leave requests (For Managers)
    public List<LeaveRequest> getAllRequests() {
        return leaveRequests;
    }
}