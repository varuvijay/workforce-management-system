package com.varun.workforce_management.leave_module.service;

import java.util.List;

import com.varun.workforce_management.leave_module.dto.LeaveApplyRequest;
import com.varun.workforce_management.leave_module.dto.LeaveResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface LeaveService {
    LeaveResponse applyLeave(@Valid LeaveApplyRequest request, Principal principal);

    List<LeaveResponse> getMyLeaves(Principal principal);

    LeaveResponse cancelLeave(Long leaveId, Principal principal);

    List<LeaveResponse> getPendingLeavesForManager(Principal principal);

    LeaveResponse approveLeave(Long leaveId, Principal principal, String comments);

    LeaveResponse rejectLeave(Long leaveId, Principal principal, String comments);
}
