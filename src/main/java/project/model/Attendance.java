package project.model;

import java.util.ArrayList;
import java.util.List;

import project.status.AttendanceStatus;

public class Attendance {
    private Long staffId;
    private String date;
    private AttendanceStatus status;
    
    private static List<Attendance> attendanceRecords = new ArrayList<>();

    public Attendance() {}
    
    public Attendance(Long staffId, AttendanceStatus status) {
        this.staffId = staffId;
        this.status = status;
        this.date = java.time.LocalDate.now().toString();
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
    
    public static void addAttendance(Attendance attendance) {
        attendanceRecords.add(attendance);
    }
    
    public static List<Attendance> getAllAttendance() {
        return attendanceRecords;
    }
}