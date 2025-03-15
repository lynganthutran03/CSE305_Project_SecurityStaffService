package project.service;

import org.springframework.stereotype.Service;
import project.model.Attendance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    private final List<Attendance> attendanceList = new ArrayList<>(); // In-memory storage

    public List<Attendance> getAllAttendance() {
        return new ArrayList<>(attendanceList);
    }

    public Optional<Attendance> getAttendanceByIdentityNumber(String identityNumber) {
        return attendanceList.stream()
                .filter(attendance -> attendance.getIdentityNumber() == identityNumber)
                .findFirst();
    }

    public List<Attendance> getAttendanceByStaff(String identityNumber) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            if (attendance.getIdentityNumber() == identityNumber) {
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

    public Attendance markAttendance(Attendance attendance) {
        attendanceList.add(attendance);
        return attendance;
    }

    public void deleteAttendance(String identityNumber) {
        attendanceList.removeIf(attendance -> attendance.getIdentityNumber() == identityNumber);
    }
}