package com.varun.workforce_management.project_module.controller;

import com.varun.workforce_management.project_module.dto.ProjectRequestDTO;
import com.varun.workforce_management.project_module.dto.ProjectResponseDTO;
import com.varun.workforce_management.project_module.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/")
    public ResponseEntity<ProjectResponseDTO> addProject(@Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.createProject(projectRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.getProjectById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.getAllProjects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.updateProject(id, projectRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> deleteProject(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.deleteProject(id));
    }

    @PostMapping("/{id}/assign")
    public ResponseEntity<ProjectResponseDTO> assignEmployeeToProject(@PathVariable Long id,
            @RequestParam Long employeeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.assignEmployeeToProject(id, employeeId));
    }

    @PostMapping("/{id}/remove")
    public ResponseEntity<ProjectResponseDTO> removeEmployeeFromProject(@PathVariable Long id,
            @RequestParam Long employeeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.removeEmployeeFromProject(id, employeeId));
    }

}

// POST /api/projects
// - GET /api/projects/{id}
// - GET /api/projects
// - PUT /api/projects/{id}
// - DELETE /api/projects/{id}
// - POST /api/projects/{id}/assign
// - POST /api/projects/{id}/remove