package project.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    return LocalTime.parse(shiftTime, formatter);
    }

    public AttendanceStatus determineAttendanceStatus(String shiftTime) {
        try {
            LocalTime scheduledTime = parseShiftTime(shiftTime);
            LocalTime currentTime = LocalTime.now(ZoneId.of("Asia/Bangkok")); // UTC+7

            Duration diff = Duration.between(scheduledTime, currentTime);
            return diff.toMinutes() <= 10 ? AttendanceStatus.PRESENT : AttendanceStatus.ABSENT;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid shift time format: " + shiftTime);
        }
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
        System.out.println("Saving attendance: " + attendance);
        AttendanceStorage.addAttendance(attendance);
        System.out.println("Current Attendance List: " + AttendanceStorage.getAllAttendance());
    }

    public void deleteAttendance(String identityNumber) {
        attendanceList.removeIf(attendance -> attendance.getIdentityNumber().equals(identityNumber));
    }
}