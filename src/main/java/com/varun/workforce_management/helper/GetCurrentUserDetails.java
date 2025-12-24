package com.varun.workforce_management.helper;

import com.varun.workforce_management.auth_module.repository.UserRepository;
import com.varun.workforce_management.employee_module.repository.EmployeeRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GetCurrentUserDetails {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    public Employee getCurrentEmployee() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Employee employee = employeeRepository.findByUser(user);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee profile not found for user");
        }
        return employee;
    }
    
}
