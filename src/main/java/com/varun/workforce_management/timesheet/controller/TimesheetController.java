package com.varun.workforce_management.timesheet.controller;

import com.varun.workforce_management.helper.MessageResponseDTO;
import com.varun.workforce_management.timesheet.dto.*;
import com.varun.workforce_management.timesheet.service.TimesheetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/timesheets")
@RequiredArgsConstructor
public class TimesheetController {

    private final TimesheetService timesheetService;

    @PostMapping("/checkedIn")
    public ResponseEntity<MessageResponseDTO> checkedIn() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDTO("Checked in successfully"));
    }

    @PostMapping("/checkedOut")
    public ResponseEntity<TimesheetResponse> checkedOut(@Valid @RequestBody CreateTimesheetRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(timesheetService.checkedOut(request));
    }

    @GetMapping("/my")
    public ResponseEntity<TimesheetResponse> getMyTimesheet() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(timesheetService.getMyTimesheet());
    }

    // Manager related access

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<List<TimesheetResponse>> getManagerTimesheet() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(timesheetService.getManagerTimesheet());
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<MessageResponseDTO> approve(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(timesheetService.approve(id));
    }

    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<MessageResponseDTO> reject(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(timesheetService.reject(id));
    }
}
