package com.varun.workforce_management.project_assignment.dto;

import com.varun.workforce_management.project_assignment.entity.Priority;
import com.varun.workforce_management.project_assignment.entity.Status;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;

public record ProjectAssignmentRequestDTO(
                @NotNull(message = "Project ID is required") Long projectId,

                @NotNull(message = "Employee ID is required") Long employeeId,

                @NotNull(message = "Task Name is required") String taskName,

                @NotNull(message = "Feature Name is required") String featureName,

                @NotNull(message = "Task Description is required") String taskDescription,

                @NotNull(message = "Due Date is required") Instant dueDate,

                @NotNull(message = "Status is required") Status status,

                @NotNull(message = "Priority is required") Priority priority) {
}
