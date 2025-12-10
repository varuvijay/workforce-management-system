package com.varun.workforce_management.project_module.service;

import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.employee_module.repository.EmployeeRepository;
import com.varun.workforce_management.exception.ResourceNotFoundException;
import com.varun.workforce_management.exception.UserExistsException;
import com.varun.workforce_management.project_module.dto.ProjectRequestDTO;
import com.varun.workforce_management.project_module.dto.ProjectResponseDTO;
import com.varun.workforce_management.project_module.entity.Project;
import com.varun.workforce_management.project_module.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    @PreAuthorize("hasRole('MANAGER')")
    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {
        if (projectRepository.existsByProjectName(projectRequestDTO.projectName())) {
            throw new UserExistsException("Project with name " + projectRequestDTO.projectName() + " already exists");
        }

        Employee manager = employeeRepository.findById(projectRequestDTO.managerId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Manager not found with id: " + projectRequestDTO.managerId()));

        Project project = Project.from(projectRequestDTO);
        project.setManager(manager);

        Project savedProject = projectRepository.save(project);

        return ProjectResponseDTO.from(savedProject);
    }

    @Override
    @PreAuthorize("hasRole('MANAGER')")
    public ProjectResponseDTO getProjectById(Long id) {
        return ProjectResponseDTO.from(
                projectRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Project not found")));
    }

    @Override
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectResponseDTO::from)
                .toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('MANAGER')")
    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO projectRequestDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        project.setProjectName(projectRequestDTO.projectName());
        project.setDescription(projectRequestDTO.description());
        project.setStartDate(projectRequestDTO.startDate());
        project.setEndDate(projectRequestDTO.endDate());
        project.setStatus(projectRequestDTO.status());

        if (projectRequestDTO.managerId() != null) {
            Employee manager = employeeRepository.findById(projectRequestDTO.managerId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Manager not found with id: " + projectRequestDTO.managerId()));
            project.setManager(manager);
        }

        return ProjectResponseDTO.from(projectRepository.save(project));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('MANAGER')")
    public ProjectResponseDTO deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        projectRepository.delete(project);
        return ProjectResponseDTO.from(project);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('MANAGER')")
    public ProjectResponseDTO assignEmployeeToProject(Long projectId, Long employeeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        if (project.getEmployees() == null) {
            project.setEmployees(new java.util.HashSet<>());
        }

        project.getEmployees().add(employee);
        return ProjectResponseDTO.from(projectRepository.save(project));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('MANAGER')")
    public ProjectResponseDTO removeEmployeeFromProject(Long projectId, Long employeeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        if (project.getEmployees() != null) {
            project.getEmployees().remove(employee);
        }

        return ProjectResponseDTO.from(projectRepository.save(project));
    }

}
