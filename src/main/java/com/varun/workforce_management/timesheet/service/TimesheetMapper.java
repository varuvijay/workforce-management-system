package com.varun.workforce_management.timesheet.service;

import com.varun.workforce_management.timesheet.entity.Timesheet;
import com.varun.workforce_management.timesheet.dto.TimesheetResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Component
public class TimesheetMapper {

    public TimesheetResponse toResponseDTO(Timesheet timesheet) {
        BigDecimal hoursWorked = BigDecimal.ZERO;
        if (timesheet.getCheckedIn() != null && timesheet.getCheckedOut() != null) {
            long minutes = Duration.between(timesheet.getCheckedIn(), timesheet.getCheckedOut()).toMinutes();
            hoursWorked = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        }

        return new TimesheetResponse(
                timesheet.getId(),
                timesheet.getWorkDate(),
                hoursWorked,
                timesheet.getTaskDescription(),
                timesheet.getStatus(),
                timesheet.getApprovedBy() != null
                        ? timesheet.getApprovedBy().getFirstName() + " " + timesheet.getApprovedBy().getLastName()
                        : null,
                timesheet.getApprovedAt(),
                timesheet.getCreatedAt(),
                timesheet.getUpdatedAt());
    }

    public List<TimesheetResponse> toResponseDTOs(List<Timesheet> timesheets) {
        return timesheets.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

}
