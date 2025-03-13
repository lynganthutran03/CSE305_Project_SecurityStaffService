package project.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule")
public class SecuritySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private SecurityStaff staff;

    private String place;
    private String shiftTime;
    private String date;

    @Column(name = "created_at", updatable = false)
    private String createdAt;
}