package project.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import project.model.Schedule;

@Service
public class ScheduleService {
    private final List<Schedule> schedules = new ArrayList<>();

    public boolean isScheduleValid(Schedule newSchedule) {
        return schedules.stream()
            .noneMatch(s -> s.getIdentityNumber().equals(newSchedule.getIdentityNumber()) &&
                            s.getDate().equals(newSchedule.getDate()) &&
                            s.getPlace().equalsIgnoreCase(newSchedule.getPlace()) &&
                            s.getShiftTime().equals(newSchedule.getShiftTime()));
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
        return schedules.stream()
            .filter(schedule -> schedule.getIdentityNumber().equalsIgnoreCase(identityNumber))
            .collect(Collectors.toList());
    }

    
    public Optional<Schedule> getScheduleForStaff(String identityNumber, LocalDate date) {
        return schedules.stream()
            .filter(schedule -> schedule.getIdentityNumber().equals(identityNumber) && schedule.getDate().equals(date))
            .findFirst();
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

    public void updateSchedule(Schedule schedule) {
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getIdentityNumber().equals(schedule.getIdentityNumber())) {
                schedules.set(i, schedule);
                return;
            }
        }
    }    

    public boolean deleteSchedule(String identityNumber) {
        return schedules.removeIf(schedule -> schedule.getIdentityNumber().equals(identityNumber));
    }
}