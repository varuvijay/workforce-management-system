package com.varun.workforce_management.leave_module.dto;

import java.time.LocalDate;

import com.varun.workforce_management.leave_module.entity.LeaveStatus;
import com.varun.workforce_management.leave_module.entity.LeaveType;

public record LeaveResponse(
        Long id,
        LocalDate startDate,
        LocalDate endDate,
        LeaveType leaveType,
        LeaveStatus status,
        Integer numberOfDays,
        String managerName,
        String comments) {
}
