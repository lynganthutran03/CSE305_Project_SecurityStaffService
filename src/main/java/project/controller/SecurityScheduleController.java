package project.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.model.SecuritySchedule;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/schedules")
public class SecurityScheduleController {
    private List<SecuritySchedule> schedules = new ArrayList<>();

    @PostMapping("/add")
    public ResponseEntity<String> addSchedule(@RequestBody SecuritySchedule schedule) {
        schedules.add(schedule);
        return new ResponseEntity<>("Schedule added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<List<SecuritySchedule>> getAllSchedules() {
        return ResponseEntity.ok(schedules);
    }

    @PutMapping("/update/{staffId}")
    public ResponseEntity<String> updateSchedule(@PathVariable int staffId, @RequestBody SecuritySchedule updatedSchedule) {
        for (SecuritySchedule schedule : schedules) {
            if (schedule.getStaffId() == staffId) {
                schedule.setStaffId(updatedSchedule.getStaffId());
                schedule.setDate(updatedSchedule.getDate());
                schedule.setShiftTime(updatedSchedule.getShiftTime());
                return ResponseEntity.ok("Schedule updated successfully");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Schedule not found");
    }

    @DeleteMapping("/delete/{staffId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable int staffId) {
        schedules.removeIf(schedule -> schedule.getStaffId() == staffId);
        return ResponseEntity.ok("Schedule deleted successfully");
    }
}