package com.varun.workforce_management.auth_module.service;

import com.varun.workforce_management.auth_module.dto.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Map;


public interface AuthService {
    public ResponseEntity<Map<String, String>> registerUser(RegistrationRequest registerResponse) ;
}
