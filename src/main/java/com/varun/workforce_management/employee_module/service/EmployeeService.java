package com.varun.workforce_management.employee_module.service;


import com.varun.workforce_management.employee_module.dto.EmployeeRequest;
import com.varun.workforce_management.employee_module.dto.Message;
import jakarta.validation.Valid;

import java.util.List;

public interface EmployeeService {

    Message addEmployee(@Valid EmployeeRequest employeeRequest);

    List<Message> getAllEmployees();
}
