package project.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping("/add")
    public ResponseEntity<String> addSchedule(@RequestBody Schedule schedule) {
        scheduleService.addSchedule(schedule);
        return new ResponseEntity<>("Schedule added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @PutMapping("/update/{identityNumber}")
    public ResponseEntity<String> updateSchedule(@PathVariable String identityNumber, @RequestBody Schedule updatedSchedule) {
        Optional<Schedule> optionalSchedule = scheduleService.getScheduleById(identityNumber);
        if (optionalSchedule.isPresent()) {
            Schedule existingSchedule = optionalSchedule.get();
            existingSchedule.setDate(updatedSchedule.getDate());
            existingSchedule.setShiftTime(updatedSchedule.getShiftTime());
            existingSchedule.setPlace(updatedSchedule.getPlace());

            scheduleService.updateSchedule(existingSchedule);
            return ResponseEntity.ok("Schedule updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Schedule not found");
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Schedule>> getFilterSchedules(@RequestParam(required = false) String identityNumber) {
        if (identityNumber == null || identityNumber.isBlank()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    
        List<Schedule> filteredSchedules = scheduleService.getSchedulesByStaff(identityNumber);
        return ResponseEntity.ok(filteredSchedules);
    }

    @DeleteMapping("/delete/{identityNumber}")
        public ResponseEntity<String> deleteSchedule(@PathVariable String identityNumber) {
        boolean removed = scheduleService.deleteSchedule(identityNumber);
        if (removed) {
            return ResponseEntity.ok("Schedule deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Schedule not found");
        }
    }
}