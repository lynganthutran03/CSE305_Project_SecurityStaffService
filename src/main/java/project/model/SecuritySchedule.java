package project.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SecuritySchedule {
    private Long staffId;
    private String place;
    private String shiftTime;
    private LocalDate date;
    private static List<SecuritySchedule> schedules = new ArrayList<>();

    public SecuritySchedule() {}

    public SecuritySchedule(Long staffId, String place, String shiftTime, LocalDate date) {
        this.staffId = staffId;
        this.place = place;
        this.shiftTime = shiftTime;
        this.date = date;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public static void addSchedule(SecuritySchedule schedule) {
        schedules.add(schedule);
    }

    public static List<SecuritySchedule> getAllSchedules() {
        return schedules;
    }
}