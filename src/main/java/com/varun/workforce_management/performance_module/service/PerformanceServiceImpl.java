package com.varun.workforce_management.performance_module.service;

import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.employee_module.repository.EmployeeRepository;
import com.varun.workforce_management.exception.custom.ResourceNotFoundException;
import com.varun.workforce_management.performance_module.dto.PerformanceResponse;
import com.varun.workforce_management.performance_module.dto.PerformanceReviewRequest;
import com.varun.workforce_management.performance_module.entity.Performance;
import com.varun.workforce_management.performance_module.repository.PerformanceRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public PerformanceResponse giveRating(Long employeeId, PerformanceReviewRequest request, User managerUser) {
        Employee manager = getEmployeeByUser(managerUser);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        // Validation: Manager can only rate their direct reports
        if (employee.getManagerId() == null
                || !employee.getManagerId().getEmployeeId().equals(manager.getEmployeeId())) {
            throw new IllegalArgumentException("You are not the manager of this employee");
        }

        Performance performance = new Performance();
        performance.setEmployee(employee);
        performance.setManager(manager);
        performance.setQuarter(request.quarter());
        performance.setRating(request.rating());
        performance.setComments(request.comments());

        Performance savedPerformance = performanceRepository.save(performance);
        return mapToResponse(savedPerformance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PerformanceResponse> getMyPerformance(User user) {
        Employee employee = getEmployeeByUser(user);
        return performanceRepository.findByEmployee(employee).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PerformanceResponse> getTeamPerformance(User managerUser) {
        Employee manager = getEmployeeByUser(managerUser);
        return performanceRepository.findByManager(manager).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PerformanceResponse> getEmployeePerformance(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        return performanceRepository.findByEmployee(employee).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Employee getEmployeeByUser(User user) {
        Employee employee = employeeRepository.findByUser(user);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee profile not found for user: " + user.getEmail());
        }
        return employee;
    }

    private PerformanceResponse mapToResponse(Performance performance) {
        return new PerformanceResponse(
                performance.getId(),
                performance.getEmployee().getEmployeeId(),
                performance.getEmployee().getFirstName() + " " + performance.getEmployee().getLastName(),
                performance.getManager().getEmployeeId(),
                performance.getManager().getFirstName() + " " + performance.getManager().getLastName(),
                performance.getQuarter(),
                performance.getRating(),
                performance.getComments(),
                performance.getCreatedAt());
    }
}
