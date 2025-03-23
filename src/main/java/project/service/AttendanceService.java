package project.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import project.model.Attendance;
import project.status.AttendanceStatus;
import project.storage.AttendanceStorage;

@Service
public class AttendanceService {

    private final List<Attendance> attendanceList = new ArrayList<>();

    private LocalTime parseShiftTime(String shiftTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
        return LocalTime.parse(shiftTime, formatter);
    }

    public AttendanceStatus determineAttendanceStatus(String shiftTime) {
        LocalTime shiftStartTime = parseShiftTime(shiftTime);
        LocalTime now = LocalTime.now();

        if (!now.isAfter(shiftStartTime.plusMinutes(10))) {
            return AttendanceStatus.PRESENT;
        }
        return AttendanceStatus.ABSENT;
    }

    public List<Attendance> getAllAttendance() {
        return AttendanceStorage.getAllAttendance();
    }

    public Optional<Attendance> getAttendanceByIdentityNumber(String identityNumber) {
        return attendanceList.stream()
                .filter(attendance -> attendance.getIdentityNumber().equals(identityNumber))
                .findFirst();
    }

    public List<Attendance> getAttendanceByStaff(String identityNumber) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            if (attendance.getIdentityNumber().equals(identityNumber)) {
                result.add(attendance);
            }
        }
        return result;
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            if (attendance.getDate().equals(date)) {
                result.add(attendance);
            }
        }
        return result;
    }

    public void markAttendance(Attendance attendance) {
        AttendanceStorage.addAttendance(attendance);
    }

    public void deleteAttendance(String identityNumber) {
        attendanceList.removeIf(attendance -> attendance.getIdentityNumber().equals(identityNumber));
    }
}