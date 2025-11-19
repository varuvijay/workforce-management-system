package com.varun.workforce_management.auth_module.service;

import com.varun.workforce_management.auth_module.dto.LoginRequest;
import com.varun.workforce_management.auth_module.dto.RegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Map;


public interface AuthService {
    public ResponseEntity<Map<String, String>> registerUser(RegistrationRequest registerResponse) ;

    ResponseEntity<Map<String, String>> loginUser(@Valid LoginRequest loginRequest);
}
