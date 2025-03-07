package project.model;

public class SecurityStaff {
    private Long id;
    private String name;
    private String role;
    private String shift;
    
    public SecurityStaff(Long id, String name, String role, String shift) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.shift = shift;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
