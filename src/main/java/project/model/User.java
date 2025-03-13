package project.model;

public class User {

    private static long counter = 1; // Used for generating unique IDs
    private Long id;
    private String name;
    private String identityNumber;

    public User(String name, String identityNumber) {
        this.id = counter++;
        this.name = name;
        this.identityNumber = identityNumber;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIdentityNumber() { return identityNumber; }
    public void setIdentityNumber(String identityNumber) { this.identityNumber = identityNumber; }
}