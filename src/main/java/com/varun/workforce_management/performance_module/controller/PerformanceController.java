package com.varun.workforce_management.performance_module.controller;

import com.varun.workforce_management.auth_module.dto.UserPrincipal;
import com.varun.workforce_management.performance_module.dto.PerformanceResponse;
import com.varun.workforce_management.performance_module.dto.PerformanceReviewRequest;
import com.varun.workforce_management.performance_module.service.PerformanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/performance")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @PostMapping("/{employeeId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<PerformanceResponse> giveRating(
            @PathVariable Long employeeId,
            @Valid @RequestBody PerformanceReviewRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(performanceService.giveRating(employeeId, request, userPrincipal.getUser()));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'HR', 'ADMIN')")
    public ResponseEntity<List<PerformanceResponse>> getMyPerformance(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(performanceService.getMyPerformance(userPrincipal.getUser()));
    }

    @GetMapping("/team")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<PerformanceResponse>> getTeamPerformance(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(performanceService.getTeamPerformance(userPrincipal.getUser()));
    }

    @GetMapping("/employee/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PerformanceResponse>> getEmployeePerformance(@PathVariable Long id) {
        return ResponseEntity.ok(performanceService.getEmployeePerformance(id));
    }
}
