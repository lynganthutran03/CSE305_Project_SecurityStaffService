package project.service;

import org.springframework.stereotype.Service;
import project.model.SecuritySchedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SecurityScheduleService {
    private List<SecuritySchedule> schedules = new ArrayList<>();

    public List<SecuritySchedule> getAllSchedules() {
        return schedules;
    }

    public Optional<SecuritySchedule> getScheduleById(Long staffId) {
        return schedules.stream()
                .filter(schedule -> schedule.getStaffId().equals(staffId))
                .findFirst();
    }

    public List<SecuritySchedule> getSchedulesByStaff(Long staffId) {
        List<SecuritySchedule> staffSchedules = new ArrayList<>();
        for (SecuritySchedule schedule : schedules) {
            if (schedule.getStaffId().equals(staffId)) {
                staffSchedules.add(schedule);
            }
        }
        return staffSchedules;
    }

    public List<SecuritySchedule> getSchedulesByPlace(String place) {
        List<SecuritySchedule> placeSchedules = new ArrayList<>();
        for (SecuritySchedule schedule : schedules) {
            if (schedule.getPlace().equalsIgnoreCase(place)) {
                placeSchedules.add(schedule);
            }
        }
        return placeSchedules;
    }

    public SecuritySchedule addSchedule(SecuritySchedule schedule) {
        schedules.add(schedule);
        return schedule;
    }

    public boolean deleteSchedule(Long staffId) {
        return schedules.removeIf(schedule -> schedule.getStaffId().equals(staffId));
    }
}