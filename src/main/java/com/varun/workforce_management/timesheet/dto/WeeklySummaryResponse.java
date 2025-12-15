package com.varun.workforce_management.timesheet.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record WeeklySummaryResponse(
                LocalDate weekStart,
                LocalDate weekEnd,
                BigDecimal totalHours) {
}
