package com.varun.workforce_management.timesheet.service;

import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.auth_module.repository.UserRepository;
import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.employee_module.repository.EmployeeRepository;
import com.varun.workforce_management.exception.ResourceNotFoundException;
import com.varun.workforce_management.helper.GetCurrentUserDetails;
import com.varun.workforce_management.helper.MessageResponseDTO;
import com.varun.workforce_management.timesheet.entity.Timesheet;
import com.varun.workforce_management.timesheet.entity.TimesheetStatus;
import com.varun.workforce_management.timesheet.repository.TimesheetRepository;
import com.varun.workforce_management.timesheet.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class TimesheetServiceImpl implements TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final GetCurrentUserDetails getCurrentUserDetails;
    private final TimesheetMapper timesheetMapper;

    @Override
    public MessageResponseDTO checkedIn() {
        Employee employee = getCurrentUserDetails.getCurrentEmployee();
        Timesheet timesheet = timesheetRepository.findByEmployeeAndWorkDate(employee, LocalDate.now());
        if (timesheet != null) {
            throw new ResourceNotFoundException("Timesheet already exists for this employee");
        }
        timesheet = Timesheet.createCheckIn(employee);
        timesheetRepository.save(timesheet);
        return new MessageResponseDTO("Checked in successfully");
    }

    @Override
    public TimesheetResponse checkedOut(CreateTimesheetRequest request) {
        return null;
    }

    @Override
    public TimesheetResponse getMyTimesheet() {
        return null;
    }

    @Override
    public List<TimesheetResponse> getManagerTimesheet() {
        return List.of();
    }

    @Override
    public MessageResponseDTO approve(Long id) {
        return null;
    }

    @Override
    public MessageResponseDTO reject(Long id) {
        return null;
    }

    @Override
    public TimesheetResponse create(CreateTimesheetRequest request) {
        return null;
    }

}
