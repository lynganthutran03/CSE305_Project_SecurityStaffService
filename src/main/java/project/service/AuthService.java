package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.model.User;
import project.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public void register(String identityNumber, String password, String role) {
        User user = new User();
        user.setIdentityNumber(identityNumber);
        user.setPassword(password);
        user.setRole(role);
        userRepository.save(user);
    }

    public User authenticate(String identityNumber, String password) {
        User user = userRepository.findByIdentityNumber(identityNumber)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return user;
    }
}