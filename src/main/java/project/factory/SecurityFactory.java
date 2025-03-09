package project.factory;

import java.util.ArrayList;
import java.util.List;

import project.model.SecurityPerson;

public class SecurityFactory {
    private static List<SecurityPerson> staffList = new ArrayList<>();

    public static SecurityPerson createStaff(String identityNumber, String name, String password, String role) {
        SecurityPerson staff = new SecurityPerson(identityNumber, name, password, role);
        staffList.add(staff);
        return staff;
    }

    public static List<SecurityPerson> getAllStaff() {
        return staffList;
    }

    public static SecurityPerson getStaffById(String identityNumber) {
        return staffList.stream()
                        .filter(staff -> staff.getIdentityNumber().equals(identityNumber))
                        .findFirst()
                        .orElse(null);
    }
}
