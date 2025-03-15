package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.repository.UserRepository;
import project.request.LoginRequest;
import project.service.AuthService;
import project.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOptional = userRepository.findByIdentityNumber(request.getIdentityNumber()); // ✅ Fixed

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(request.getPassword())) { 
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("role", user.getRole());
                return ResponseEntity.ok(response);
                }
        }

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("message", "Invalid identity number or password");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        authService.register(username, password, role);
        return ResponseEntity.ok("User registered successfully");
    }
}