package project.service;

import java.util.List;

import project.factory.SecurityStaffFactory;
import project.model.SecurityStaff;

public class SecurityStaffService {
    public SecurityStaff addStaff(Long id, String name, String role, String shift) {
        return SecurityStaffFactory.createStaff(id, name, role, shift);
    }

    public List<SecurityStaff> getAllStaff () {
        return SecurityStaffFactory.getAllStaff();
    }

    public SecurityStaff findStaffById(int id) {
        return SecurityStaffFactory.getStaffById(id);
    }
}
