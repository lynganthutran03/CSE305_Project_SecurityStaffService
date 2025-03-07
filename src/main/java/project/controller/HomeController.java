package project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return """
        Welcome to Security Management System! Available Endpoints:
        1. GET /staff/all - Get all security staff
        2. GET /staff/{id} - Get staff by ID
        3. POST /staff/add - Add new staff
        4. PUT /staff/update/{id} - Update staff details
        5. DELETE /staff/delete/{id} - Delete staff
        """;
    }
}
