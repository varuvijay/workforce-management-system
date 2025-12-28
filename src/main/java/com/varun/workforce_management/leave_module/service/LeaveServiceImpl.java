package com.varun.workforce_management.leave_module.service;

import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.employee_module.repository.EmployeeRepository;
import com.varun.workforce_management.leave_module.dto.LeaveApplyRequest;
import com.varun.workforce_management.leave_module.dto.LeaveResponse;
import com.varun.workforce_management.leave_module.entity.LeaveRequest;
import com.varun.workforce_management.leave_module.entity.LeaveStatus;
import com.varun.workforce_management.leave_module.repository.LeaveRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

   
}
