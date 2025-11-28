package com.varun.workforce_management.project_module.dto;

import com.varun.workforce_management.project_module.entity.ProjectStatus;

import java.time.Instant;
import java.time.LocalDate;

public record ProjectResponseDTO(
        Long projectId,
        String projectName,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        ProjectStatus status,
        String managerName,
        Long managerId,
        Instant createdAt,
        Instant updatedAt) {
}
