package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.model.SecurityStaff;

@Repository
public interface SecurityStaffRepository extends JpaRepository<SecurityStaff, Long> {
    // Custom queries can be added here if needed
}
