package project.controller;

import org.springframework.web.bind.annotation.*;
import project.model.Attendance;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")  // Allow frontend requests
public class AttendanceController {
    private final List<Attendance> attendanceRecords = new ArrayList<>();

    // ✅ Use @RequestBody instead of @RequestParam
    @PostMapping("/mark")
    public String markAttendance(@RequestBody Attendance attendance) {
        attendanceRecords.add(attendance);
        return "Attendance marked successfully for Staff ID: " + attendance.getStaffId();
    }

    @GetMapping("/view")
    public List<Attendance> viewAttendance() {
        return attendanceRecords;
    }
}
