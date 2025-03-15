package project.controller;

import org.springframework.web.bind.annotation.*;
import project.service.SalaryService;

@RestController
@RequestMapping("/api/salary")
@CrossOrigin(origins = "*")
public class SalaryController {
    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/{identityNumber}")
    public double getSalary(@PathVariable String identityNumber) {
        return salaryService.calculateSalary(identityNumber);
    }
}