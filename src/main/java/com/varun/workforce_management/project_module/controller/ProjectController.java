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

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    
}
