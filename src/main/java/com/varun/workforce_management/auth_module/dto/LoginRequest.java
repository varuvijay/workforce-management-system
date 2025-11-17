package com.varun.workforce_management.auth_module.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 8, max = 20, message = "Password must be between 7 and 20 characters")
    @NotBlank(message = "Password cannot be blank")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "Password must contain at least one uppercase letter, one lowercase "
            + "letter, one digit, and one special character"
    )
    private String password;
}
