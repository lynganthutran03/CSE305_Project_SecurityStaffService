package project.service;

import org.springframework.stereotype.Service;
import project.model.Schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private List<Schedule> schedules = new ArrayList<>();

    public List<Schedule> getAllSchedules() {
        return schedules;
    }

    public Optional<Schedule> getScheduleById(Long staffId) {
        return schedules.stream()
                .filter(schedule -> schedule.getStaffId().equals(staffId))
                .findFirst();
    }

    public List<Schedule> getSchedulesByStaff(Long staffId) {
        List<Schedule> staffSchedules = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getStaffId().equals(staffId)) {
                staffSchedules.add(schedule);
            }
        }
        return staffSchedules;
    }

    public List<Schedule> getSchedulesByPlace(String place) {
        List<Schedule> placeSchedules = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getPlace().equalsIgnoreCase(place)) {
                placeSchedules.add(schedule);
            }
        }
        return placeSchedules;
    }

    public Schedule addSchedule(Schedule schedule) {
        schedules.add(schedule);
        return schedule;
    }

    public boolean deleteSchedule(Long staffId) {
        return schedules.removeIf(schedule -> schedule.getStaffId().equals(staffId));
    }
}