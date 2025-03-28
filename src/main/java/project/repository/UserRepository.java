package project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdentityNumber(String identityNumber);
}   