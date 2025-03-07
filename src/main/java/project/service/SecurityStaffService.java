package project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.factory.SecurityStaffFactory;
import project.model.SecurityStaff;
import project.repository.SecurityStaffRepository;

@Service
public class SecurityStaffService {

    @Autowired
    private SecurityStaffRepository repository;

    // Add new security staff
    public SecurityStaff addStaff(SecurityStaff staff) {
        SecurityStaff newStaff = SecurityStaffFactory.createStaff(staff.getId(), staff.getName(), staff.getRole(), staff.getShift());
        return repository.save(newStaff);
    }    

    // Retrieve all staff members
    public List<SecurityStaff> getAllStaff() {
        return repository.findAll();
    }

    // Find staff by ID
    public SecurityStaff findStaffById(Long id) {
        Optional<SecurityStaff> staff = repository.findById(id);
        return staff.orElse(null);
    }

    public SecurityStaff updateStaff(Long id, SecurityStaff staffDetails) {
        SecurityStaff staff = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found"));
        staff.setName(staffDetails.getName());
        staff.setRole(staffDetails.getRole());
        staff.setShift(staffDetails.getShift());
        return repository.save(staff);
    }

    public void deleteStaff(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found");
        }
        repository.deleteById(id);
    }
}
