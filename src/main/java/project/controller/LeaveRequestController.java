package project.controller;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<ResponseMessage> updateLeaveStatus(@PathVariable("leaveId") int leaveId, 
                                                         @RequestBody LeaveStatusUpdateRequest statusUpdate) {
        LeaveRequest leaveRequest = LeaveRequestStorage.getAllLeaveRequests()
            .stream()
            .filter(leave -> leave.getLeaveId() == leaveId)
            .findFirst()
            .orElse(null);

        if (leaveRequest == null) {
            return new ResponseEntity<>(new ResponseMessage("Leave request not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }

        try {
            LeaveStatus leaveStatus = LeaveStatus.valueOf(statusUpdate.getStatus().toUpperCase()); // Convert String to Enum
            leaveRequest.setStatus(leaveStatus);
            return new ResponseEntity<>(new ResponseMessage("Leave request updated successfully", HttpStatus.OK.value()), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage("Invalid status: " + statusUpdate.getStatus(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/staff/{identityNumber}")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestByStaff(@PathVariable("identityNumber") String identityNumber) {
        List<LeaveRequest> staffLeave = LeaveRequestStorage.getAllLeaveRequests()
            .stream()
            .filter(leave -> leave.getIdentityNumber().equals(identityNumber))
            .toList();
            return ResponseEntity.ok(staffLeave.isEmpty() ? Collections.emptyList() : staffLeave);
    }

    @GetMapping("/count/{identityNumber}")
    public ResponseEntity<Integer> getApprovedLeaveCount(@PathVariable("identityNumber") String identityNumber) {
        int count = (int)LeaveRequestStorage.getAllLeaveRequests()
            .stream()
            .filter(request -> Objects.equals(request.getIdentityNumber(), identityNumber) && request.getStatus() == LeaveStatus.APPROVED)
            .count();

        return ResponseEntity.ok(count);
    }
}