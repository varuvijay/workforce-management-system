package com.varun.workforce_management.project_assignment.controller;

import com.varun.workforce_management.project_assignment.dto.MessageResponseDTO;
import com.varun.workforce_management.project_assignment.dto.ProjectAssignmentRequestDTO;
import com.varun.workforce_management.project_assignment.dto.ProjectAssignmentResponseDTO;
import com.varun.workforce_management.project_assignment.service.ProjectAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.varun.workforce_management.project_assignment.dto.AssignmentStatusUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assignment")
@RequiredArgsConstructor
public class ProjectAssignmentController {

    private final ProjectAssignmentService projectAssignmentService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/")
    public ResponseEntity<ProjectAssignmentResponseDTO> createAssignment(
            @RequestBody ProjectAssignmentRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectAssignmentService.createAssignment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectAssignmentResponseDTO> getAssignmentById(@PathVariable Long id) {
        return ResponseEntity.ok(projectAssignmentService.getById(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my-tasks")
    public ResponseEntity<Page<ProjectAssignmentResponseDTO>> myTasks(
            Pageable pageable) {
        return ResponseEntity.ok(projectAssignmentService.getMyAssignments(pageable));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteAssignment(@PathVariable Long id) {
        projectAssignmentService.deleteAssignment(id);
        return ResponseEntity.ok(new MessageResponseDTO("deleted"));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProjectAssignmentResponseDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentStatusUpdateRequest req) {
        return ResponseEntity.ok(projectAssignmentService.updateStatus(id, req.getStatus()));
    }

}
