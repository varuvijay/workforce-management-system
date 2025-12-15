package com.varun.workforce_management.employee_module.dto;

import com.varun.workforce_management.employee_module.entity.Employee;
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

        public static EmployeeResponseDTO from(Employee employee) {
                return new EmployeeResponseDTO(
                                employee.getEmployeeId(),
                                employee.getFirstName(),
                                employee.getLastName(),
                                employee.getUser().getEmail(),
                                employee.getPhoneNumber(),
                                employee.getDateOfBirth(),
                                employee.getAddress(),
                                employee.getPermanentAddress(),
                                employee.getDateOfJoining(),
                                employee.getDesignation() != null ? employee.getDesignation().getDesignationName()
                                                : null,
                                employee.getGender(),
                                employee.getPanNumber(),
                                employee.getAadharNumber(),
                                employee.getBankAccountNumber(),
                                employee.getBankName(),
                                employee.getIfscCode(),
                                employee.getBranch(),
                                employee.getSalary(),
                                employee.getCreatedAt(),
                                employee.getUpdatedAt());
        }
}
