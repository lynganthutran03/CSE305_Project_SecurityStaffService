package project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.model.LeaveRequest;
import project.model.LeaveStatus;
import project.service.LeaveRequestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*") // Allow frontend requests
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @PostMapping("/request")
    public ResponseEntity<LeaveRequest> requestLeave(@RequestBody LeaveRequest leaveRequest) {
        LeaveRequest savedRequest = leaveRequestService.requestLeave(leaveRequest);
        return ResponseEntity.ok(savedRequest);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<LeaveRequest>> getPendingRequests() {
        return ResponseEntity.ok(leaveRequestService.getPendingRequests());
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveLeave(@PathVariable Long id) {
        Optional<LeaveRequest> request = leaveRequestService.updateLeaveStatus(id, LeaveStatus.APPROVED);
        return request.isPresent() ? ResponseEntity.ok("Leave Approved") : ResponseEntity.notFound().build();
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<String> rejectLeave(@PathVariable Long id) {
        Optional<LeaveRequest> request = leaveRequestService.updateLeaveStatus(id, LeaveStatus.REJECTED);
        return request.isPresent() ? ResponseEntity.ok("Leave Rejected") : ResponseEntity.notFound().build();
    }
}