package com.varun.workforce_management.auth_module.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 40, message = "Username must be between 3 and 40 characters")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Size(min = 8, max = 20, message = "Password must be between 7 and 20 characters")
    @NotBlank(message = "Password cannot be blank")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "Password must contain at least one uppercase letter, one lowercase "
                    + "letter, one digit, and one special character"
    )
    private String password;

    @NotBlank(message = "Role cannot be blank")
    private String role;
}
