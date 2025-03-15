package project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class LoginController {
    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("staff1", "staff");
        users.put("manager1", "manager");
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String username = request.get("username");

        if (users.containsKey(username)) {
            String role = users.get(username);
            session.setAttribute("user", username);
            session.setAttribute("role", role);

            response.put("status", "success");
            response.put("role", role);
        } else {
            response.put("status", "fail");
            response.put("message", "Invalid credentials");
        }

        return response;
    }

    @PostMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("status", "logged out");
        return response;
    }
}