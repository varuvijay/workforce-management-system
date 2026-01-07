package com.varun.workforce_management.leave_module.controller;

import com.varun.workforce_management.leave_module.dto.LeaveApplyRequest;
import com.varun.workforce_management.leave_module.dto.LeaveResponse;
import com.varun.workforce_management.leave_module.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/apply")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<LeaveResponse> applyLeave(@Valid @RequestBody LeaveApplyRequest request,
            Principal principal) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(leaveService.applyLeave(request, principal));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<LeaveResponse>> getMyLeaves(Principal principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(leaveService.getMyLeaves(principal));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<LeaveResponse> cancelLeave(@PathVariable Long id, Principal principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(leaveService.cancelLeave(id, principal));
    }

    // --- Manager Endpoints ---

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<List<LeaveResponse>> getPendingLeaves(Principal principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(leaveService.getPendingLeavesForManager(principal));
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<LeaveResponse> approveLeave(@PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body,
            Principal principal) {
        String comments = body != null ? body.get("comments") : null;
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(leaveService.approveLeave(id, principal, comments));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<LeaveResponse> rejectLeave(@PathVariable Long id,
                                                     @RequestBody(required = false) Map<String, String> body,
                                                     Principal principal) {
        String comments = body != null ? body.get("comments") : null;
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(leaveService.rejectLeave(id, principal, comments));
    }
}