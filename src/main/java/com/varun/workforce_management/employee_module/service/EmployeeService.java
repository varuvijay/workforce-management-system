package com.varun.workforce_management.employee_module.service;

import com.varun.workforce_management.employee_module.dto.EmployeeCreateRequest;
import com.varun.workforce_management.employee_module.dto.EmployeeResponseDTO;
import com.varun.workforce_management.employee_module.dto.Message;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO addEmployee(@Valid EmployeeCreateRequest employeeCreateRequest) ;

    List<EmployeeResponseDTO> getAllEmployees(int page, int size);

    EmployeeResponseDTO getEmployeeByEmail(@Email String email);

    EmployeeResponseDTO updateEmployee(@Valid EmployeeCreateRequest employeeCreateRequest, @Email String email);

    Message deleteEmployee(@Email String email);

}
