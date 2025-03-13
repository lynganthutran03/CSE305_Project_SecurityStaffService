package project.request;

import java.time.LocalDate;

import project.status.LeaveStatus;

public class LeaveRequest {
    private static int idCounter = 1;  // Static counter to generate unique leave IDs
    private int leaveId;
    private Long staffId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LeaveStatus status;

    public LeaveRequest() {
        this.leaveId = idCounter++;  // Assign a unique ID and increment the counter
    }

    public LeaveRequest(Long staffId, LocalDate startDate, LocalDate endDate, String reason, LeaveStatus status) {
        this();
        this.staffId = staffId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
    }

    public int getLeaveId() {
        return leaveId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }
}