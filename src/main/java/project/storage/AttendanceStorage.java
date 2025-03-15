package project.storage;

import project.model.Attendance;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceStorage {
    private static List<Attendance> attendanceList = new ArrayList<>();

    public static void addAttendance(Attendance attendance) {
        attendanceList.add(attendance);
    }

    public static List<Attendance> getAllAttendance() {
        return attendanceList;
    }

    public static List<Attendance> findByStaffId(String identityNumber) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance att : attendanceList) {
            if (att.getIdentityNumber().equals(identityNumber)) {
                result.add(att);
            }
        }
        return result;
    }

    public static List<Attendance> findByDate(LocalDate date) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance att : attendanceList) {
            if (att.getDate().equals(date)) {
                result.add(att);
            }
        }
        return result;
    }
}