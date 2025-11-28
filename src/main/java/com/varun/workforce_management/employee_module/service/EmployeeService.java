package com.varun.workforce_management.employee_module.service;

import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.employee_module.dto.EmployeeRequest;
import com.varun.workforce_management.employee_module.dto.EmployeeResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO addEmployee(@Valid EmployeeRequest employeeRequest, User user);

    List<EmployeeResponseDTO> getAllEmployees();
}
