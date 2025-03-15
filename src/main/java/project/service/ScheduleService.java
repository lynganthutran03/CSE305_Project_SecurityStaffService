package project.service;

import org.springframework.stereotype.Service;
import project.model.Schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private List<Schedule> schedules = new ArrayList<>();

    public boolean isScheduleValid(Schedule newSchedule) {
        return schedules.stream()
            .noneMatch(s -> s.getStaffId().equals(newSchedule.getStaffId()) &&
                            s.getDate().equals(newSchedule.getDate()) &&
                            s.getPlace().equalsIgnoreCase(newSchedule.getPlace()));
    }

    public Schedule addSchedule(Schedule schedule) {
        if(isScheduleValid(schedule)) {
            schedules.add(schedule);
            return schedule;
        }
        else {
            throw new IllegalArgumentException("Schedule conflict! Staff is already assigned on that day");
        }
    }

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

    public List<Schedule> filterSchedules(Long staffId, LocalDate date, String place) {
        return schedules.stream()
            .filter(s -> (staffId == null || s.getStaffId().equals(staffId)))
            .filter(s -> (date == null || s.getDate().equals(date)))
            .filter(s -> (place == null || s.getPlace().equalsIgnoreCase(place)))
            .collect(Collectors.toList());
    }

    public boolean deleteSchedule(Long staffId) {
        return schedules.removeIf(schedule -> schedule.getStaffId().equals(staffId));
    }
}