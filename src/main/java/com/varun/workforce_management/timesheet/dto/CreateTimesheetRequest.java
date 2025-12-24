package com.varun.workforce_management.timesheet.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTimesheetRequest(

    @NotBlank(message = "Task description is required")
    String taskDescription
) {}
