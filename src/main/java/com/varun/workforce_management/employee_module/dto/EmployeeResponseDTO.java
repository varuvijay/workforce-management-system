package com.varun.workforce_management.employee_module.dto;

import com.varun.workforce_management.employee_module.entity.Gender;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record EmployeeResponseDTO(
        Long employeeId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth,
        String address,
        String permanentAddress,
        LocalDate dateOfJoining,
        String designationName,
        Gender gender,
        String panNumber,
        String aadharNumber,
        String bankAccountNumber,
        String bankName,
        String ifscCode,
        String branch,
        BigDecimal salary,
        Instant createdAt,
        Instant updatedAt) {
}
