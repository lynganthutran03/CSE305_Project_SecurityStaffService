package project.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.model.Schedule;
import project.service.ScheduleService;

@RestController
@RequestMapping("/api/monitoring")
@CrossOrigin(origins = "*")
public class RoutineMonitorController {
    private final ScheduleService scheduleService;

    @Autowired
    public RoutineMonitorController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    
    @GetMapping("/schedules")
    public ResponseEntity<List<Schedule>> getSchedules(
            @RequestParam(required = false) Long staffId, @RequestParam(required = false) LocalDate date, @RequestParam(required = false) String place) {

        List<Schedule> schedules = scheduleService.filterSchedules(staffId, date, place);
        if(staffId != null) {
            schedules = schedules.stream()
                                .filter(s -> s.getStaffId().equals(staffId))
                                .collect(Collectors.toList());
        }
        if(date != null) {
            schedules = schedules.stream()
                                .filter(s -> s.getDate().equals(date))
                                .collect(Collectors.toList());
        }
        if(place != null) {
            schedules = schedules.stream()
                                .filter(s -> s.getPlace().equalsIgnoreCase(place))
                                .collect(Collectors.toList());
        }
        if(schedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(schedules);
    }
}
