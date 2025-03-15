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
            .noneMatch(s -> s.getIdentityNumber().equals(newSchedule.getIdentityNumber()) &&
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

    public Optional<Schedule> getScheduleById(String identityNumber) {
        return schedules.stream()
                .filter(schedule -> schedule.getIdentityNumber().equals(identityNumber))
                .findFirst();
    }

    public List<Schedule> getSchedulesByStaff(String identityNumber) {
        List<Schedule> staffSchedules = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getIdentityNumber().equals(identityNumber)) {
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

    public List<Schedule> filterSchedules(String identityNumber, LocalDate date, String place) {
        return schedules.stream()
            .filter(s -> (identityNumber == null || s.getIdentityNumber().equals(identityNumber)))
            .filter(s -> (date == null || s.getDate().equals(date)))
            .filter(s -> (place == null || s.getPlace().equalsIgnoreCase(place)))
            .collect(Collectors.toList());
    }

    public boolean deleteSchedule(String identityNumber) {
        return schedules.removeIf(schedule -> schedule.getIdentityNumber().equals(identityNumber));
    }
}