package com.varun.workforce_management.auth_module.service;

import com.varun.workforce_management.auth_module.dto.LoginRequest;
import com.varun.workforce_management.auth_module.dto.RegistrationRequest;
import com.varun.workforce_management.auth_module.entity.Role;
import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.auth_module.repository.RoleRepository;
import com.varun.workforce_management.auth_module.repository.UserRepository;
import com.varun.workforce_management.exception.UserExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;


    @Override
    public ResponseEntity<Map<String, String>> registerUser(RegistrationRequest registrationRequest) {

        if(userRepository.existsByEmail(registrationRequest.getEmail()))
            throw new UserExistsException("User already exists");
        if(!roleRepository.existsByRoleName(registrationRequest.getRole().toUpperCase()))
            throw new UserExistsException("Role does not exist");
        registrationRequest.setPassword(passwordService.encodePassword(registrationRequest.getPassword()));

        Role role = roleRepository.findByRoleName(registrationRequest.getRole().toUpperCase())
                .orElseThrow(() -> new UserExistsException("Role not found"));

        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(registrationRequest.getPassword());
        user.setIsActive(true);
        user.setRole(role);

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }

    @Override
    public ResponseEntity<Map<String, String>> loginUser(LoginRequest loginRequest) {

        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if(authentication.isAuthenticated()) {
            String token = jwtService.generateToken(loginRequest.getEmail());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of("token", token));
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Authentication failed"));
        }
    }


}
