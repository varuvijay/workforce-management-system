package com.varun.workforce_management.project_module.dto;

import com.varun.workforce_management.project_module.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ProjectRequestDTO(
        @NotBlank(message = "Project name is required")
        String projectName,

        @NotBlank(message = "Project description is required")
        String description,

        @NotNull(message = "Project startDate is required")
        LocalDate startDate,

        @NotNull(message = "Project endDate is required")
        LocalDate endDate,

        @NotNull(message = "Status is required")
        ProjectStatus status,

        @NotNull(message = "ManagerId is required")
        Long managerId) {
}
