package project.model;

public class Staff {
    
    private static long counter = 1;
    private Long id;
    private String name;

    public Staff(String name) {
        this.id = counter++;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}