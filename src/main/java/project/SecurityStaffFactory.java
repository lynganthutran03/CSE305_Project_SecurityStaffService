package project;

import java.util.ArrayList;
import java.util.List;

public class SecurityStaffFactory {
    private static List<SecurityStaff> staffList = new ArrayList<>();

    public static SecurityStaff createStaff(Long id, String name, String role, String shift) {
        SecurityStaff staff = new SecurityStaff(id, name, role, shift);
        staffList.add(staff);
        return staff;
    }

    public static List<SecurityStaff> getAllStaff() {
        return staffList;
    }

    public static SecurityStaff getStaffById(int id) {
        return staffList.stream().filter(staff -> staff.getId() == id).findFirst().orElse(null);
    }
}
