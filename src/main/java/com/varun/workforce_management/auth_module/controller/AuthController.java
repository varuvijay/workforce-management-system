package com.varun.workforce_management.auth_module.controller;

import com.varun.workforce_management.auth_module.dto.*;
import com.varun.workforce_management.auth_module.service.AuthService;
import jakarta.validation.Valid;

import com.varun.workforce_management.auth_module.dto.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.loginUser(loginRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logoutUser(@RequestBody RefreshTokenRequest refreshToken) {
        return ResponseEntity.ok(authService.logoutUser(refreshToken));
    }
}
