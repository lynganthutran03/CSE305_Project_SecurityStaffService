package project.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.model.LeaveRequest;
import project.model.LeaveStatus;

import java.util.List;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByStatus(LeaveStatus status);
}
