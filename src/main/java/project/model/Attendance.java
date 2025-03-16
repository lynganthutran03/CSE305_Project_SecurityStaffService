package project.model;

import project.status.AttendanceStatus;

public class Attendance {
    private String identityNumber;
    private String date;
    private String place;
    private AttendanceStatus status;

    public Attendance(String identityNumber, String place, AttendanceStatus status) {
        this.identityNumber = identityNumber;
        this.date = java.time.LocalDate.now().toString();
        this.place = place;
        this.status = status;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
}