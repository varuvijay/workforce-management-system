package com.varun.workforce_management.project_assignment.service;

import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.project_assignment.dto.ProjectAssignmentRequestDTO;
import com.varun.workforce_management.project_assignment.dto.ProjectAssignmentResponseDTO;
import com.varun.workforce_management.project_assignment.entity.ProjectAssignment;
import com.varun.workforce_management.project_module.entity.Project;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ProjectAssignmentMapper {

    public ProjectAssignment toEntity(ProjectAssignmentRequestDTO request, Project project, Employee employee,
            Employee assignedBy) {
        return ProjectAssignment.builder()
                .project(project)
                .employee(employee)
                .assignedBy(assignedBy)
                .taskName(request.taskName())
                .featureName(request.featureName())
                .taskDescription(request.taskDescription())
                .dueDate(request.dueDate())
                .assignedDate(Instant.now())
                .status(request.status())
                .priority(request.priority())
                .build();
    }

    public ProjectAssignmentResponseDTO toResponseDTO(ProjectAssignment projectAssignment) {
        return ProjectAssignmentResponseDTO.builder()
                .id(projectAssignment.getId())
                .projectId(projectAssignment.getProject().getProjectId())
                .employeeId(projectAssignment.getEmployee().getEmployeeId())
                .assignedById(projectAssignment.getAssignedBy().getEmployeeId())
                .taskName(projectAssignment.getTaskName())
                .featureName(projectAssignment.getFeatureName())
                .taskDescription(projectAssignment.getTaskDescription())
                .dueDate(projectAssignment.getDueDate())
                .assignedDate(projectAssignment.getAssignedDate())
                .status(projectAssignment.getStatus().name())
                .priority(projectAssignment.getPriority().name())
                .createdAt(projectAssignment.getCreatedAt())
                .updatedAt(projectAssignment.getUpdatedAt())
                .build();
    }
}
