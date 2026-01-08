package com.varun.workforce_management.performance_module.service;

import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.performance_module.dto.PerformanceResponse;
import com.varun.workforce_management.performance_module.dto.PerformanceReviewRequest;

import java.util.List;

public interface PerformanceService {
    PerformanceResponse giveRating(Long employeeId, PerformanceReviewRequest request, User managerUser);

    List<PerformanceResponse> getMyPerformance(User user);

    List<PerformanceResponse> getTeamPerformance(User managerUser);

    List<PerformanceResponse> getEmployeePerformance(Long employeeId);
}
