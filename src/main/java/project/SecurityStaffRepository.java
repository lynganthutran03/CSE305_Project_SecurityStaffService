package project;

import project.SecurityStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityStaffRepository extends JpaRepository<SecurityStaff, Long> {
    // Custom queries can be added here if needed
}
