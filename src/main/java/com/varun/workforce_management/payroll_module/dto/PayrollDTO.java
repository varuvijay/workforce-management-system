package com.varun.workforce_management.payroll_module.dto;

import com.varun.workforce_management.payroll_module.entity.PayrollStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record PayrollDTO(
        Long id,
        Long employeeId,
        String employeeName,
        LocalDate month,
        BigDecimal basicSalary,
        BigDecimal allowances,
        BigDecimal deductions,
        BigDecimal netSalary,
        Instant generatedAt,
        PayrollStatus status) {
}
