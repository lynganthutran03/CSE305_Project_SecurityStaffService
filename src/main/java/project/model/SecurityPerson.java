package project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "security_staff")

public class SecurityPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String identityNumber;
    private String name;
    private String password;
    private String role; //Staff or Manager

    public SecurityPerson() {}
    
    public SecurityPerson(String identityNumber, String name, String password, String role) {
        this.identityNumber = identityNumber;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setId(String identityNumber) {
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
