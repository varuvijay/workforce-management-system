package com.varun.workforce_management.auth_module.service;

import com.varun.workforce_management.auth_module.dto.LoginRequest;
import com.varun.workforce_management.auth_module.dto.RegistrationRequest;
import jakarta.validation.Valid;
import com.varun.workforce_management.auth_module.dto.LoginResponse;
import com.varun.workforce_management.auth_module.dto.RegisterResponse;

import com.varun.workforce_management.auth_module.dto.RefreshTokenRequest;
import com.varun.workforce_management.auth_module.dto.TokenRefreshResponse;

public interface AuthService {
    RegisterResponse registerUser(RegistrationRequest registerResponse);

    LoginResponse loginUser(@Valid LoginRequest loginRequest);

    TokenRefreshResponse refreshToken(RefreshTokenRequest request);
}
