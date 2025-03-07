package project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import project.model.SecurityStaff;
import project.repository.SecurityStaffRepository;

public class SecurityStaffController {
    private final SecurityStaffRepository repository;

    public SecurityStaffController(SecurityStaffRepository repository) {
        this.repository = repository;
    }

    public List<SecurityStaff> getAllStaff() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public SecurityStaff getStaffById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping("/{id}")
    public SecurityStaff addStaff(@RequestBody SecurityStaff staff) {
        return repository.save(staff);
    }

    @PutMapping("/{id}")
    public SecurityStaff updateStaff(@PathVariable Long id, @RequestBody SecurityStaff updatedStaff) {
        return repository.findById(id).map(staff -> {
            staff.setName(updatedStaff.getName());
            staff.setRole(updatedStaff.getRole());
            staff.setShift(updatedStaff.getShift());
            return repository.save(staff);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteStaff(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
