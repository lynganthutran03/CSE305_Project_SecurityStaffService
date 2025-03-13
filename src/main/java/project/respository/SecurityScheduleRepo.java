package project.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.SecuritySchedule;
import java.util.List;

@Repository
public interface SecurityScheduleRepo extends JpaRepository<SecuritySchedule, Long> {
    
    // Find schedules by staff ID
    List<SecuritySchedule> findByStaffId(Long staffId);

    // Find schedules by place
    List<SecuritySchedule> findByPlace(String place);
}