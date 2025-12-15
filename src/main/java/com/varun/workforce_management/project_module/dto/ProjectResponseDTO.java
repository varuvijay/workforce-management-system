package com.varun.workforce_management.project_module.dto;

import com.varun.workforce_management.project_module.entity.Project;
import com.varun.workforce_management.project_module.entity.ProjectStatus;
import lombok.Data;

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

        public static ProjectResponseDTO from(Project project) {
                return new ProjectResponseDTO(
                                project.getProjectId(),
                                project.getProjectName(),
                                project.getDescription(),
                                project.getStartDate(),
                                project.getEndDate(),
                                project.getStatus(),
                                project.getManager().getFirstName(),
                                project.getManager().getEmployeeId(),
                                project.getCreatedAt(),
                                project.getUpdatedAt());
        }
}
