package project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.model.Attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {
    private final List<Attendance> attendanceRecords = new ArrayList<>();

    @PostMapping("/mark")
    public ResponseEntity<Object> markAttendance(@RequestBody Attendance attendance) {
        attendanceRecords.add(attendance);
        return ResponseEntity.ok(new HashMap<String, String>() {{
        put("message", "Attendance marked successfully for Staff ID: " + attendance.getIdentityNumber());
    }});
}

    @GetMapping("/view")
    public List<Attendance> viewAttendance() {
        return attendanceRecords;
    }
}
