package project.model;

import java.time.LocalDate;

import project.status.AttendanceStatus;

public class Attendance {
    private String identityNumber;
    private LocalDate date;
    private AttendanceStatus status;
    private String shiftTime;

    public Attendance(String identityNumber, LocalDate date, AttendanceStatus status, String shiftTime) {
        this.identityNumber = identityNumber;
        this.date = date;
        this.status = status;
        this.shiftTime = shiftTime;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setStaffId(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }
}