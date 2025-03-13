package project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.model.ResponseMessage;
import project.request.LeaveRequest;
import project.request.LeaveStatusUpdateRequest;
import project.status.LeaveStatus;
import project.storage.LeaveRequestStorage;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*")
public class LeaveRequestController {

    // Use LeaveRequestStorage to manage leave requests
    @PostMapping("/request")
    public ResponseEntity<ResponseMessage> requestLeave(@RequestBody LeaveRequest request) {
        request.setStatus(LeaveStatus.PENDING); // Default to PENDING
        LeaveRequestStorage.addLeaveRequest(request); // Add request to storage
        ResponseMessage responseMessage = new ResponseMessage("Leave request submitted successfully", HttpStatus.CREATED.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests() {
        return ResponseEntity.ok(LeaveRequestStorage.getAllLeaveRequests()); // Get all leave requests from storage
    }

    @PutMapping("/{leaveId}/status")
    public ResponseEntity<String> updateLeaveStatus(@PathVariable("leaveId") int leaveId, 
                                                 @RequestBody LeaveStatusUpdateRequest statusUpdate) {
        LeaveRequest leaveRequest = LeaveRequestStorage.getAllLeaveRequests()
            .stream()
            .filter(leave -> leave.getLeaveId() == leaveId)
            .findFirst()
            .orElse(null);

        if (leaveRequest == null) {
            return new ResponseEntity<>("Leave request not found", HttpStatus.NOT_FOUND);
        }

        try {
            LeaveStatus leaveStatus = LeaveStatus.valueOf(statusUpdate.getStatus().toUpperCase()); // Convert String to Enum
            leaveRequest.setStatus(leaveStatus);
            return new ResponseEntity<>("Leave request updated successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid status: " + statusUpdate.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }
}