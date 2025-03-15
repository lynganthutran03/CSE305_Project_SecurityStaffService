package project.storage;

import project.model.Schedule;
import java.util.ArrayList;
import java.util.List;

public class ScheduleStorage {
    private static List<Schedule> schedules = new ArrayList<>();

    public static void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public static List<Schedule> getAllSchedules() {
        return schedules;
    }

    public static List<Schedule> findByStaffId(String identityNumber) {
        List<Schedule> result = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getIdentityNumber() == identityNumber) {
                result.add(schedule);
            }
        }
        return result;
    }

    public static List<Schedule> findByPlace(String place) {
        List<Schedule> result = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getPlace().equalsIgnoreCase(place)) {
                result.add(schedule);
            }
        }
        return result;
    }
}