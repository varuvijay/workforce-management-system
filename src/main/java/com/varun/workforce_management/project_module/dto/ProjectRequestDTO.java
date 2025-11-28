package com.varun.workforce_management.project_module.dto;

import com.varun.workforce_management.project_module.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProjectRequestDTO(
        @NotBlank(message = "Project name is required") String projectName,

        String description,

        LocalDate startDate,

        LocalDate endDate,

        @NotNull(message = "Status is required") ProjectStatus status,

        Long managerId) {
}
