package com.varun.workforce_management.performance_module.dto;

import com.varun.workforce_management.performance_module.entity.Quarter;
import java.time.Instant;

public record PerformanceResponse(
        Long id,
        Long employeeId,
        String employeeName,
        Long managerId,
        String managerName,
        Quarter quarter,
        Integer rating,
        String comments,
        Instant createdAt) {
}
