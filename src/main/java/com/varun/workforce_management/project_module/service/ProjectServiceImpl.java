package com.varun.workforce_management.project_module.service;

import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.employee_module.repository.EmployeeRepository;
import com.varun.workforce_management.exception.UserExistsException;
import com.varun.workforce_management.project_module.dto.ProjectRequestDTO;
import com.varun.workforce_management.project_module.dto.ProjectResponseDTO;
import com.varun.workforce_management.project_module.entity.Project;
import com.varun.workforce_management.project_module.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    
}
