package project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "security_person")

public class SecurityPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identityNumber;
    private String name;
    private String password;
    private String role; //Staff or Manager

    public SecurityPerson() {}
    
    public SecurityPerson(Long identityNumber, String name, String password, String role) {
        this.identityNumber = identityNumber;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Long getIdentityNumber() {
        return identityNumber;
    }

    public void setId(Long identityNumber) {
        this.identityNumber = identityNumber;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
