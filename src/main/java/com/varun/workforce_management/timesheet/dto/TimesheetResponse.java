package com.varun.workforce_management.timesheet.dto;

import com.varun.workforce_management.timesheet.entity.TimesheetStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record TimesheetResponse(
                Long id,
                LocalDate workDate,
                BigDecimal hoursWorked,
                String taskDescription,
                TimesheetStatus status,
                String approvedBy,
                Instant approvedAt,
                Instant createdAt,
                Instant updatedAt) {
}
