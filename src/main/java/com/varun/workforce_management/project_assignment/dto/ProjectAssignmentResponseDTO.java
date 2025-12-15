package com.varun.workforce_management.project_assignment.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ProjectAssignmentResponseDTO(

        Long id,
        Long projectId,
        Long employeeId,
        Long assignedById,
        String taskName,
        String featureName,
        String taskDescription,
        Instant assignedDate,
        Instant dueDate,
        String status,
        String priority,
        Instant createdAt,
        Instant updatedAt) {
}
