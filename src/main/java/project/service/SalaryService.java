package project.service;

import org.springframework.stereotype.Service;

@Service
public class SalaryService {

    private static final double BASE_SALARY = 2000.0;
    private static final double FINE_PER_EXTRA_LEAVE = 50.0;
    private static final int ALLOWED_LEAVES = 3;

    private final LeaveRequestService leaveRequestService;

    public SalaryService(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    public double calculateSalary(String identityNumber) {
        int approvedLeaves = leaveRequestService.getApprovedLeaveCount(identityNumber);
        int extraLeaves = Math.max(0, approvedLeaves - ALLOWED_LEAVES);
        double fine = extraLeaves * FINE_PER_EXTRA_LEAVE;
        return BASE_SALARY - fine;
    }
}