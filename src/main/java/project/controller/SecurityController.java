package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.model.SecurityPerson;
import project.service.SecurityService;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class SecurityController {

    @Autowired
    private SecurityService service;

    // Create new staff
    @PostMapping("/add")
    public ResponseEntity<SecurityPerson> addStaff(@RequestBody SecurityPerson staff) {
        SecurityPerson addStaff = service.addStaff(staff);
        return new ResponseEntity<>(addStaff, HttpStatus.CREATED);
    }

    // Get all staff
    @GetMapping("/all")
    public List<SecurityPerson> getAllStaff() {
        return service.getAllStaff();
    }

    // Find staff by Identity number
    @GetMapping("/{identityNumber}")
    public ResponseEntity<SecurityPerson> getStaffByIdentityNumber(@PathVariable String identityNumber) {
        SecurityPerson staff = service.findStaffById(identityNumber);
        return ResponseEntity.ok(staff);
    }

    // Update staff details
    @PutMapping("/update/{identityNumber}")
    public ResponseEntity<SecurityPerson> updateStaff(@PathVariable String identityNumber, @RequestBody SecurityPerson staffDetails) {
        SecurityPerson updateStaff = service.updateStaff(identityNumber, staffDetails);
        return ResponseEntity.ok(updateStaff);
    }

    // Delete staff by ID
    @DeleteMapping("/delete/{identityNumber}")
    public ResponseEntity<String> deleteStaff(@PathVariable String identityNumber) {
        service.deleteStaff(identityNumber);
        return ResponseEntity.ok("Staff deleted successfully!");
    }
}
