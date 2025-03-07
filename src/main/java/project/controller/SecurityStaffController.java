package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import project.model.SecurityStaff;
import project.repository.SecurityStaffRepository;
import project.service.SecurityStaffService;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class SecurityStaffController {

    @Autowired
    private SecurityStaffService service;

    @Autowired
    private SecurityStaffRepository repository;

    // Create new staff
    @PostMapping("/add")
    public ResponseEntity<SecurityStaff> addStaff(@RequestBody SecurityStaff staff) {
        SecurityStaff savedStaff = service.addStaff(staff);
        return new ResponseEntity<>(savedStaff, HttpStatus.CREATED);
    }

    // Get all staff
    @GetMapping("/all")
    public List<SecurityStaff> getAllStaff() {
        return service.getAllStaff();
    }

    // Find staff by ID
    @GetMapping("/{id}")
    public SecurityStaff getStaffById(@PathVariable Long id) {
        return service.findStaffById(id);
    }

    // Find staff by ID with exception handling
    public SecurityStaff findStaffById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found"));
    }

    // Update staff details
    @PutMapping("/update/{id}")
    public SecurityStaff updateStaff(@PathVariable Long id, @RequestBody SecurityStaff staffDetails) {
        return service.updateStaff(id, staffDetails);
    }

// Delete staff by ID
    @DeleteMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        service.deleteStaff(id);
        return "Staff deleted successfully!";
    }
}
