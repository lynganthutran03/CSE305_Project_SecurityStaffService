package project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import project.model.LeaveRequest;
import project.model.LeaveStatus;
import project.respository.LeaveRequestRepo;

@Service
public class LeaveRequestService {
    private final LeaveRequestRepo leaveRequestRepository;

    public LeaveRequestService(LeaveRequestRepo leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    public LeaveRequest requestLeave(LeaveRequest leaveRequest) {
        leaveRequest.setStatus(LeaveStatus.PENDING);
        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getPendingRequests() {
        return leaveRequestRepository.findByStatus(LeaveStatus.PENDING);
    }

    public Optional<LeaveRequest> updateLeaveStatus(Long id, LeaveStatus status) {
        Optional<LeaveRequest> request = leaveRequestRepository.findById(id);
        request.ifPresent(r -> {
            r.setStatus(status);
            leaveRequestRepository.save(r);
        });
        return request;
    }
}