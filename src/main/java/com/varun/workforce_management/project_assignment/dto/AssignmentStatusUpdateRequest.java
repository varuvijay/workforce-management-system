package com.varun.workforce_management.project_assignment.dto;

import com.varun.workforce_management.project_assignment.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignmentStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private Status status;
}
