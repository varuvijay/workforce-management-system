package com.varun.workforce_management.leave_module.service;

import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.employee_module.repository.EmployeeRepository;
import com.varun.workforce_management.leave_module.dto.LeaveApplyRequest;
import com.varun.workforce_management.leave_module.dto.LeaveResponse;
import com.varun.workforce_management.leave_module.entity.LeaveRequest;
import com.varun.workforce_management.leave_module.entity.LeaveStatus;
import com.varun.workforce_management.leave_module.repository.LeaveRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public LeaveResponse applyLeave(LeaveApplyRequest request, Principal principal) {
        Employee employee = getEmployee(principal);

        if (request.startDate().isAfter(request.endDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        boolean overlap = leaveRepository.existsOverlappingLeave(
                employee,
                List.of(LeaveStatus.APPLIED, LeaveStatus.APPROVED),
                request.startDate(),
                request.endDate());

        if (overlap) {
            throw new IllegalStateException("Leave overlaps with an existing application");
        }

        long days = java.time.temporal.ChronoUnit.DAYS.between(request.startDate(), request.endDate()) + 1;

        LeaveRequest leave = LeaveRequest.builder()
                .employee(employee)
                .startDate(request.startDate())
                .endDate(request.endDate())
                .leaveType(request.leaveType())
                .reason(request.reason())
                .status(LeaveStatus.APPLIED)
                .numberOfDays((int) days)
                .build();

        LeaveRequest saved = leaveRepository.save(leave);
        return mapToResponse(saved);
    }

    @Override
    public List<LeaveResponse> getMyLeaves(Principal principal) {
        Employee employee = getEmployee(principal);
        return leaveRepository.findByEmployeeOrderByStartDateDesc(employee).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LeaveResponse cancelLeave(Long leaveId, Principal principal) {
        Employee employee = getEmployee(principal);
        LeaveRequest leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new EntityNotFoundException("Leave not found"));

        if (!leave.getEmployee().equals(employee)) {
            throw new AccessDeniedException("You can only cancel your own leaves");
        }

        if (leave.getStatus() != LeaveStatus.APPLIED) {
            throw new IllegalStateException("Only APPLIED leaves can be cancelled");
        }

        leave.setStatus(LeaveStatus.CANCELLED);
        leaveRepository.save(leave);
        return mapToResponse(leave);
    }

    @Override
    public List<LeaveResponse> getPendingLeavesForManager(Principal principal) {
        Employee manager = getEmployee(principal);
        // Assuming 'managerId' in Employee is the manager object
        return leaveRepository.findByEmployee_ManagerIdAndStatusOrderByAppliedAtDesc(manager, LeaveStatus.APPLIED)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LeaveResponse approveLeave(Long leaveId, Principal principal, String comments) {
        return processLeaveAction(leaveId, principal, comments, LeaveStatus.APPROVED);
    }

    @Override
    @Transactional
    public LeaveResponse rejectLeave(Long leaveId, Principal principal, String comments) {
        return processLeaveAction(leaveId, principal, comments, LeaveStatus.REJECTED);
    }

    private LeaveResponse processLeaveAction(Long leaveId, Principal principal, String comments,
            LeaveStatus newStatus) {
        Employee manager = getEmployee(principal);
        LeaveRequest leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new EntityNotFoundException("Leave not found"));

        Employee employee = leave.getEmployee();
        if (employee.getManagerId() == null || !employee.getManagerId().equals(manager)) {
            throw new AccessDeniedException("You are not the manager of this employee");
        }

        if (leave.getStatus() != LeaveStatus.APPLIED) {
            throw new IllegalStateException("Leave is not in APPLIED status");
        }

        if (employee.equals(manager)) {
            throw new IllegalStateException("Manager cannot approve their own leave");
        }

        leave.setStatus(newStatus);
        leave.setActionedBy(manager);
        leave.setActionedAt(java.time.Instant.now());
        leave.setComments(comments);

        LeaveRequest saved = leaveRepository.save(leave);
        return mapToResponse(saved);
    }

    private Employee getEmployee(Principal principal) {
        return employeeRepository.findByUser_Email(principal.getName());
    }

    private LeaveResponse mapToResponse(LeaveRequest leave) {
        String managerName = leave.getActionedBy() != null
                ? leave.getActionedBy().getFirstName() + " " + leave.getActionedBy().getLastName()
                : (leave.getEmployee().getManagerId() != null
                        ? leave.getEmployee().getManagerId().getFirstName() + " "
                                + leave.getEmployee().getManagerId().getLastName()
                        : "N/A");

        return new LeaveResponse(
                leave.getId(),
                leave.getStartDate(),
                leave.getEndDate(),
                leave.getLeaveType(),
                leave.getStatus(),
                leave.getNumberOfDays(),
                managerName,
                leave.getComments());
    }
}
