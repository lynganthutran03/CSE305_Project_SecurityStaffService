package project.storage;

import project.request.LeaveRequest;
import project.status.LeaveStatus;

import java.util.ArrayList;
import java.util.List;

public class LeaveRequestStorage {
    private static List<LeaveRequest> leaveRequests = new ArrayList<>();

    public static void addLeaveRequest(LeaveRequest leaveRequest) {
        leaveRequests.add(leaveRequest);
    }

    public static List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequests;
    }

    public static List<LeaveRequest> findByStatus(String status) {
        List<LeaveRequest> result = new ArrayList<>();
        try {
            LeaveStatus statusEnum = LeaveStatus.valueOf(status.toUpperCase()); // Convert String to Enum
            for (LeaveRequest leave : leaveRequests) {
                if (leave.getStatus().equals(statusEnum)) {
                    result.add(leave);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status: " + status);
        }
        return result;
    }    
}