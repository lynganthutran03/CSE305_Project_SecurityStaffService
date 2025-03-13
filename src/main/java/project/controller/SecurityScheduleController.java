package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.SecuritySchedule;
import project.service.SecurityScheduleService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class SecurityScheduleController {

    @Autowired
    private SecurityScheduleService securityScheduleService;

    @GetMapping("/view")
    public ResponseEntity<List<SecuritySchedule>> viewSchedules() {
        return ResponseEntity.ok(securityScheduleService.getAllSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecuritySchedule> getScheduleById(@PathVariable Long id) {
        Optional<SecuritySchedule> schedule = securityScheduleService.getScheduleById(id);
        return schedule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSchedule() {
        return ResponseEntity.ok("Schedule added successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        securityScheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}