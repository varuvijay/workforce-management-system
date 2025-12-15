package com.varun.workforce_management.project_module.service;

import com.varun.workforce_management.project_module.dto.ProjectRequestDTO;
import com.varun.workforce_management.project_module.dto.ProjectResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface ProjectService {

    ProjectResponseDTO createProject(@Valid ProjectRequestDTO projectRequestDTO);

    ProjectResponseDTO getProjectById(Long id);

    List<ProjectResponseDTO> getAllProjects();

    ProjectResponseDTO updateProject(Long id, @Valid ProjectRequestDTO projectRequestDTO);

    ProjectResponseDTO deleteProject(Long id);

    ProjectResponseDTO assignEmployeeToProject(Long projectId, Long employeeId);

    ProjectResponseDTO removeEmployeeFromProject(Long projectId, Long employeeId);

}
