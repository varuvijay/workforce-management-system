package com.varun.workforce_management.employee_module.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record EmployeeRequest(
        @NotNull(message = "User email cannot be null") 
        @Email(message = "User email must be valid") 
        String userEmail,

        @NotBlank(message = "First name cannot be blank") 
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters") 
        String firstName,

        @NotBlank(message = "Last name cannot be blank") 
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters") 
        String lastName,

        @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits") 
        String phoneNumber,

        @Past(message = "Date of birth must be in the past") 
        LocalDate dateOfBirth,

        @Size(max = 255, message = "Address must not exceed 255 characters") 
        String address,

        @Size(max = 255, message = "Permanent address must not exceed 255 characters") 
        String permanentAddress,

        @NotNull(message = "Date of joining cannot be null") 
        LocalDate dateOfJoining,

        Long designationId,

        @NotBlank(message = "Designation name cannot be blank") 
        String designationName,

        @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")   
        String gender,

        @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$", message = "Invalid PAN format (e.g., ABCDE1234F)") 
        String panNumber,

        @Pattern(regexp = "^\\d{12}$", message = "Aadhar number must be 12 digits") 
        String aadharNumber,

        @Size(max = 20, message = "Bank account number must not exceed 20 characters") 
        String bankAccountNumber,

        @Size(max = 100, message = "Bank name must not exceed 100 characters") 
        String bankName,

        @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format") 
        String ifscCode,

        @Size(max = 100, message = "Branch name must not exceed 100 characters") 
        String branch,

        @Positive(message = "Salary must be positive") 
        @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0") 
        Double salary) {
}
