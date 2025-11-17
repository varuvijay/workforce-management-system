package com.varun.workforce_management.auth_module.service;

import com.varun.workforce_management.auth_module.dto.RegistrationRequest;
import com.varun.workforce_management.auth_module.entity.Role;
import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.auth_module.repository.RoleRepository;
import com.varun.workforce_management.auth_module.repository.UserRepository;
import com.varun.workforce_management.exception.UserExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;


    @Override
    public ResponseEntity<Map<String, String>> registerUser(RegistrationRequest registrationRequest) {

        if(userRepository.existsByEmail(registrationRequest.getEmail()))
            throw new UserExistsException("User already exists");
        if(!roleRepository.existsByRoleName(registrationRequest.getRole().toUpperCase()))
            throw new UserExistsException("Role does not exist");
        registrationRequest.setPassword(passwordService.encodePassword(registrationRequest.getPassword()));

        Optional<Role> role = roleRepository.findByRoleName(registrationRequest.getRole().toUpperCase());

        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(registrationRequest.getPassword());
        user.setIsActive(true);
        user.setRole(role.get());

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }
}
