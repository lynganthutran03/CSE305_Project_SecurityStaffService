package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.model.SecurityStaff;

@Repository
public interface SecurityStaffRepository extends JpaRepository<SecurityStaff, Long> {
    List<SecurityStaff> findByRole(String role);
}


