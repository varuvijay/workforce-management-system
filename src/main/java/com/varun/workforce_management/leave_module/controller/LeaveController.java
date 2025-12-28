package com.varun.workforce_management.leave_module.controller;

import com.varun.workforce_management.leave_module.dto.LeaveApplyRequest;
import com.varun.workforce_management.leave_module.dto.LeaveResponse;
import com.varun.workforce_management.leave_module.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    // --- Employee Endpoints ---

    public 
    

    // --- Manager Endpoints ---

  
}