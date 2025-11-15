package com.varun.workforce_management.auth_module.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
