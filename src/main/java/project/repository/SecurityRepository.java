package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.model.SecurityPerson;

@Repository
public interface SecurityRepository extends JpaRepository<SecurityPerson, String> {
    List<SecurityPerson> findByRole(String role);
}


