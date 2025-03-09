package project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.factory.SecurityFactory;
import project.model.SecurityPerson;
import project.repository.SecurityRepository;

@Service
public class SecurityService {

    @Autowired
    private SecurityRepository repository;

    // Add new security staff
    public SecurityPerson addStaff(SecurityPerson staff) {
        SecurityPerson newStaff = SecurityFactory.createStaff(staff.getIdentityNumber(), staff.getName(), staff.getPassword(), staff.getRole());
        if (newStaff == null) {
            throw new RuntimeException("Error creating staff");
        }
        return repository.save(newStaff);
    }    

    // Retrieve all staff members
    public List<SecurityPerson> getAllStaff() {
        return repository.findAll();
    }

    // Find staff by ID
    public SecurityPerson findStaffById(Long identityNumber) {
        Optional<SecurityPerson> staff = repository.findById(identityNumber);
        return staff.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found"));
    }

    //Update staff details
    public SecurityPerson updateStaff(Long identityNumber, SecurityPerson staffDetails) {
        SecurityPerson staff = repository.findById(identityNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found"));
        staff.setName(staffDetails.getName());
        staff.setPassword(staffDetails.getPassword());
        staff.setRole(staffDetails.getRole());
        return repository.save(staff);
    }

    //Delete staff by ID
    public void deleteStaff(Long identityNumber) {
        if (!repository.existsById(identityNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found");
        }
        repository.deleteById(identityNumber);
    }
}
