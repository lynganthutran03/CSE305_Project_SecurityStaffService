package project.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // Ensure the table name matches your database
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String identityNumber;
}