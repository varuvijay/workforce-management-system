package com.varun.workforce_management.auth_module.controller;

import com.varun.workforce_management.auth_module.dto.LoginRequest;
import com.varun.workforce_management.auth_module.dto.RegistrationRequest;
import com.varun.workforce_management.auth_module.service.AuthService;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return authService.registerUser(registrationRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        return authService.loginUser(loginRequest);
    }
}
