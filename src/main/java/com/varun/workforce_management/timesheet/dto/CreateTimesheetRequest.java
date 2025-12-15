package com.varun.workforce_management.timesheet.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTimesheetRequest(
    @NotNull(message = "Work date is required")
    LocalDate workDate,

    @NotNull(message = "Hours worked is required")
    @DecimalMin(value = "0.0", message = "Hours must be positive")
    @DecimalMax(value = "24.0", message = "Hours cannot exceed 24")
    BigDecimal hoursWorked,

    @NotBlank(message = "Task description is required")
    String taskDescription
) {}
