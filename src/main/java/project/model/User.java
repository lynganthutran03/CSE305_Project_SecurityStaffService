package project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identity_number", unique = true, nullable = false)
    private String identityNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // staff or manager

    public User() {}

    public User(String identityNumber, String password, String role) {
        this.identityNumber = identityNumber;
        this.password = password;
        this.role = role;
    }

    public String getIdentityNumber() {
        return identityNumber; // `username` maps to `identity_number`
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}