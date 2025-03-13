package project.storage;

import project.model.SecuritySchedule;
import java.util.ArrayList;
import java.util.List;

public class SecurityScheduleStorage {
    private static List<SecuritySchedule> schedules = new ArrayList<>();

    public static void addSchedule(SecuritySchedule schedule) {
        schedules.add(schedule);
    }

    public static List<SecuritySchedule> getAllSchedules() {
        return schedules;
    }

    public static List<SecuritySchedule> findByStaffId(int staffId) {
        List<SecuritySchedule> result = new ArrayList<>();
        for (SecuritySchedule schedule : schedules) {
            if (schedule.getStaffId() == staffId) {
                result.add(schedule);
            }
        }
        return result;
    }

    public static List<SecuritySchedule> findByPlace(String place) {
        List<SecuritySchedule> result = new ArrayList<>();
        for (SecuritySchedule schedule : schedules) {
            if (schedule.getPlace().equalsIgnoreCase(place)) {
                result.add(schedule);
            }
        }
        return result;
    }
}