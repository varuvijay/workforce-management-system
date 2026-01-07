package com.varun.workforce_management.leave_module.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

import com.varun.workforce_management.leave_module.entity.LeaveType;

public record LeaveApplyRequest(
        @NotNull(message = "Start date is required") 
        @FutureOrPresent(message = "Start date must be today or in the future") 
        LocalDate startDate,

        @NotNull(message = "End date is required") 
        @FutureOrPresent(message = "End date must be today or in the future") 
        LocalDate endDate,

        @NotNull(message = "Leave type is required") 
        LeaveType leaveType,

        @NotNull(message = "Reason is required") 
        String reason) {
}
