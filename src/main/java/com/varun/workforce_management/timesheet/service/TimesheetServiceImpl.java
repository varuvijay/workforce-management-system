package com.varun.workforce_management.timesheet.service;

import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.employee_module.repository.EmployeeRepository;
import com.varun.workforce_management.exception.ResourceNotFoundException;
import com.varun.workforce_management.helper.GetCurrentUserDetails;
import com.varun.workforce_management.helper.MessageResponseDTO;
import com.varun.workforce_management.timesheet.dto.CreateTimesheetRequest;
import com.varun.workforce_management.timesheet.dto.TimesheetResponse;
import com.varun.workforce_management.timesheet.entity.Timesheet;
import com.varun.workforce_management.timesheet.entity.TimesheetStatus;
import com.varun.workforce_management.timesheet.repository.TimesheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TimesheetServiceImpl implements TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final EmployeeRepository employeeRepository;
    private final GetCurrentUserDetails getCurrentUserDetails;
    private final TimesheetMapper timesheetMapper;

    @Override
    public MessageResponseDTO checkedIn() {
        Employee employee = getCurrentUserDetails.getCurrentEmployee();
        timesheetRepository.findByEmployeeAndWorkDate(employee, LocalDate.now())
                .ifPresent(timesheet -> {
                    throw new ResourceNotFoundException("Timesheet already exists for this employee");
                });

        Timesheet timesheet = Timesheet.createCheckIn(employee);
        timesheetRepository.save(timesheet);

        return new MessageResponseDTO("Checked in successfully");
    }

    @Override
    public TimesheetResponse checkedOut(CreateTimesheetRequest request) {
        Employee employee = getCurrentUserDetails.getCurrentEmployee();
        Timesheet timesheet = timesheetRepository.findByEmployeeAndWorkDate(employee, LocalDate.now())
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet not found for this employee"));

        timesheet.setCheckedOut(Instant.now());
        timesheet.setTaskDescription(request.taskDescription());
        Timesheet savedTimesheet = timesheetRepository.save(timesheet);

        return timesheetMapper.toResponseDTO(savedTimesheet);
    }

    @Override
    public TimesheetResponse getMyTimesheet() {
        Employee employee = getCurrentUserDetails.getCurrentEmployee();
        return timesheetMapper.toResponseDTO(timesheetRepository.findByEmployeeAndWorkDate(employee, LocalDate.now()).get());
    }

    @Override
    public List<TimesheetResponse> getManagerTimesheet() {
        Employee manager = getCurrentUserDetails.getCurrentEmployee();
        List<Employee> employees = employeeRepository.findByManagerId(manager);
        List<Timesheet> timesheets = timesheetRepository.findByEmployeeInAndWorkDate(employees, LocalDate.now());
        return timesheetMapper.toResponseDTOs(timesheets);
    }

    @Override
    public MessageResponseDTO approve(Long id) {
        Employee manager = getCurrentUserDetails.getCurrentEmployee();
        Timesheet timesheet = timesheetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet not found"));
        if (!timesheet.getEmployee().getManagerId().equals(manager)) {
            throw new AccessDeniedException("You are not authorized to approve this timesheet");
        }
        timesheet.setStatus(TimesheetStatus.APPROVED);
        timesheet.setApprovedBy(manager);
        timesheet.setApprovedAt(Instant.now());
        timesheetRepository.save(timesheet);
        return new MessageResponseDTO("Timesheet updated successfully");
    }

    @Override
    public MessageResponseDTO reject(Long id) {
        Employee manager = getCurrentUserDetails.getCurrentEmployee();
        Timesheet timesheet = timesheetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet not found"));
        if (!timesheet.getEmployee().getManagerId().equals(manager)) {
            throw new AccessDeniedException("You are not authorized to reject this timesheet");
        }
        timesheet.setStatus(TimesheetStatus.REJECTED);
        timesheet.setApprovedBy(manager);
        timesheet.setApprovedAt(Instant.now());
        timesheetRepository.save(timesheet);
        return new MessageResponseDTO("Timesheet updated successfully");
    }

}
