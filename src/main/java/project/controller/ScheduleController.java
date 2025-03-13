package project.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.model.Schedule;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    private List<Schedule> schedules = new ArrayList<>();

    @PostMapping("/add")
    public ResponseEntity<String> addSchedule(@RequestBody Schedule schedule) {
        schedules.add(schedule);
        return new ResponseEntity<>("Schedule added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(schedules);
    }

    @PutMapping("/update/{staffId}")
    public ResponseEntity<String> updateSchedule(@PathVariable int staffId, @RequestBody Schedule updatedSchedule) {
        for (Schedule schedule : schedules) {
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