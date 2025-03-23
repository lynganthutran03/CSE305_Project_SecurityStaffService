package project.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.model.Attendance;
import project.model.Schedule;
import project.service.AttendanceService;
import project.service.ScheduleService;
import project.status.AttendanceStatus;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestParam String identityNumber) {
        LocalDate today = LocalDate.now();
        Optional<Schedule> scheduledShift = scheduleService.getScheduleForStaff(identityNumber, today);

        if (scheduledShift.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No scheduled shift found for today.");
        }

        String shiftTime = scheduledShift.get().getShiftTime();
        AttendanceStatus status = attendanceService.determineAttendanceStatus(shiftTime);

        Attendance attendance = new Attendance(identityNumber, today, status, shiftTime);
        attendanceService.markAttendance(attendance);

        return ResponseEntity.ok("Attendance marked as " + status);
    }

    @GetMapping("/view")
    public ResponseEntity<List<Attendance>> viewAttendance() {
        List<Attendance> attendanceList = attendanceService.getAllAttendance();
        return ResponseEntity.ok(attendanceList);
    }
}
