package project.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;

public class Schedule {
    @Column(name = "identity_number", nullable = false)
    private String identityNumber;
    private String place;
    private String shiftTime;
    private LocalDate date;
    private static List<Schedule> schedules = new ArrayList<>();

    public Schedule() {
    }

    public Schedule(String identityNumber, String place, String shiftTime, LocalDate date) {
        this.identityNumber = identityNumber;
        this.place = place;
        this.shiftTime = formatShiftTime(shiftTime);
        this.date = date;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String formatShiftTime(String time) {
        try {
            LocalTime parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            return parsedTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid time format: " + time);
        }
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public static void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public static List<Schedule> getAllSchedules() {
        return schedules;
    }
}