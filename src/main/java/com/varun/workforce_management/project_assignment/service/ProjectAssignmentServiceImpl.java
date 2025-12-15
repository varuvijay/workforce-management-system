package com.varun.workforce_management.project_assignment.service;

import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.auth_module.repository.UserRepository;
import com.varun.workforce_management.employee_module.dto.EmployeeResponseDTO;
import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.employee_module.repository.EmployeeRepository;
import com.varun.workforce_management.exception.ResourceNotFoundException;
import com.varun.workforce_management.project_assignment.dto.ProjectAssignmentRequestDTO;
import com.varun.workforce_management.project_assignment.dto.ProjectAssignmentResponseDTO;
import com.varun.workforce_management.project_assignment.entity.ProjectAssignment;
import com.varun.workforce_management.project_assignment.repository.ProjectAssignmentRepository;
import com.varun.workforce_management.project_module.dto.ProjectResponseDTO;
import com.varun.workforce_management.project_module.entity.Project;
import com.varun.workforce_management.project_module.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectAssignmentServiceImpl implements ProjectAssignmentService {

    private final ProjectAssignmentRepository projectAssignmentRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final ProjectAssignmentMapper projectAssignmentMapper;

    @Override
    @Transactional
    public ProjectAssignmentResponseDTO createAssignment(ProjectAssignmentRequestDTO request) {
        log.info("Creating project assignment for project: {} and employee: {}", request.projectId(),
                request.employeeId());

        var project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project not found with id: " + request.projectId()));

        var employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + request.employeeId()));

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Current user not found: " + currentUserEmail));

        Employee assignedBy = employeeRepository.findByUser(currentUser);
        if (assignedBy == null) {
            throw new ResourceNotFoundException(
                    "Employee profile not found for current user: " + currentUserEmail);
        }

        var projectAssignment = projectAssignmentMapper.toEntity(request, project, employee, assignedBy);
        var savedAssignment = projectAssignmentRepository.save(projectAssignment);

        log.info("Project assignment created successfully with id: {}", savedAssignment.getId());

        return projectAssignmentMapper.toResponseDTO(savedAssignment);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectAssignmentResponseDTO getById(Long id) {
        ProjectAssignment assignment = projectAssignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assignment not found with id: " + id));
        return projectAssignmentMapper.toResponseDTO(assignment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectAssignmentResponseDTO> getMyAssignments(
           Pageable pageable) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Current user not found: " + currentUserEmail));

        Employee currentEmployee = employeeRepository.findByUser(currentUser);
        if (currentEmployee == null) {
            throw new ResourceNotFoundException(
                    "Employee profile not found for current user: " + currentUserEmail);
        }

        return projectAssignmentRepository.findByEmployee(currentEmployee, pageable)
                .map(projectAssignmentMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public void deleteAssignment(Long id) {
        if (!projectAssignmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Assignment not found with id: " + id);
        }
        projectAssignmentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProjectAssignmentResponseDTO updateStatus(Long id,
                                                     com.varun.workforce_management.project_assignment.entity.Status status) {
        ProjectAssignment assignment = projectAssignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assignment not found with id: " + id));

        assignment.setStatus(status);
        ProjectAssignment savedAssignment = projectAssignmentRepository.save(assignment);

        return projectAssignmentMapper.toResponseDTO(savedAssignment);
    }
}
