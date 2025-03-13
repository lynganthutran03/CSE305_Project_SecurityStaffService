package project.model;

public class SecurityStaff {
    
    private static long counter = 1; // Used for generating unique IDs
    private Long id;
    private String name;

    public SecurityStaff(String name) {
        this.id = counter++;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}