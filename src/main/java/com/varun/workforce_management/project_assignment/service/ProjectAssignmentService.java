package com.varun.workforce_management.project_assignment.service;

import com.varun.workforce_management.project_assignment.dto.ProjectAssignmentRequestDTO;
import com.varun.workforce_management.project_assignment.dto.ProjectAssignmentResponseDTO;
import com.varun.workforce_management.project_assignment.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectAssignmentService {

    ProjectAssignmentResponseDTO createAssignment(ProjectAssignmentRequestDTO request);

    ProjectAssignmentResponseDTO getById(Long id);

    Page<ProjectAssignmentResponseDTO> getMyAssignments(Pageable pageable);

    void deleteAssignment(Long id);

    ProjectAssignmentResponseDTO updateStatus(Long id, Status status);
}
