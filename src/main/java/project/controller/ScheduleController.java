package project.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.model.Schedule;
import project.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    private List<Schedule> schedules = new ArrayList<>();

    @PostMapping("/add")
    public ResponseEntity<String> addSchedule(@RequestBody Schedule schedule) {
        scheduleService.addSchedule(schedule);
        return new ResponseEntity<>("Schedule added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
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

    @GetMapping("/filter")
    public ResponseEntity<List<Schedule>> getFilterSchedules(
            @RequestParam(required = false) Long staffId,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String place) {
        LocalDate parsedDate = null;
        if (date != null && !date.trim().isEmpty()) {
            try {
                parsedDate = LocalDate.parse(date);
            }
            catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body(Collections.emptyList());
            }
        }
    
        List<Schedule> filteredSchedules = scheduleService.filterSchedules(staffId, parsedDate, place);
    
        return ResponseEntity.ok(filteredSchedules != null ? filteredSchedules : new ArrayList<>());
    }

    @DeleteMapping("/delete/{staffId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable int staffId) {
        boolean removed = scheduleService.deleteSchedule((long) staffId);
        if (removed) {
            return ResponseEntity.ok("Schedule deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Schedule not found");
        }
    }
}