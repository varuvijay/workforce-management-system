package com.varun.workforce_management.auth_module.service;

import com.varun.workforce_management.auth_module.dto.*;
import jakarta.validation.Valid;

import com.varun.workforce_management.auth_module.dto.RefreshTokenResponse;

public interface AuthService {
    RegisterResponse registerUser(RegistrationRequest registerResponse);

    LoginResponse loginUser(@Valid LoginRequest loginRequest);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);


    LogoutResponse logoutUser(RefreshTokenRequest refreshToken);
}
