package com.varun.workforce_management.timesheet.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public record UpdateTimesheetRequest(
                @DecimalMin(value = "0.0", message = "Hours must be positive") @DecimalMax(value = "24.0", message = "Hours cannot exceed 24") BigDecimal hoursWorked,

                String taskDescription) {
}
