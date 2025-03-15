package project.model;

import java.util.ArrayList;
import java.util.List;

import project.status.AttendanceStatus;

public class Attendance {
    private String identityNumber;
    private String date;
    private AttendanceStatus status;
    
    private static List<Attendance> attendanceRecords = new ArrayList<>();

    public Attendance() {}
    
    public Attendance(String identityNumber, AttendanceStatus status) {
        this.identityNumber = identityNumber;
        this.status = status;
        this.date = java.time.LocalDate.now().toString();
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setStaffId(String identityNumber) {
        this.identityNumber = identityNumber;
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