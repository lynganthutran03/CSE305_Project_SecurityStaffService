package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.SecuritySchedule;
import project.respository.SecurityScheduleRepo;

import java.util.List;
import java.util.Optional;

@Service
public class SecurityScheduleService {

    @Autowired
    private SecurityScheduleRepo securityScheduleRepository;

    public List<SecuritySchedule> getAllSchedules() {
        return securityScheduleRepository.findAll();
    }

    public Optional<SecuritySchedule> getScheduleById(Long id) {
        return securityScheduleRepository.findById(id);
    }

    public List<SecuritySchedule> getSchedulesByStaff(Long staffId) {
        return securityScheduleRepository.findByStaffId(staffId);
    }

    public List<SecuritySchedule> getSchedulesByPlace(String place) {
        return securityScheduleRepository.findByPlace(place);
    }

    public SecuritySchedule addSchedule(SecuritySchedule schedule) {
        return securityScheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        securityScheduleRepository.deleteById(id);
    }
}
