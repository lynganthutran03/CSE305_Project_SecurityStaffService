package project;

import java.util.List;

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
