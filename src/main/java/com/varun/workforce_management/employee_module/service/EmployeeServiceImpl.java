package com.varun.workforce_management.employee_module.service;

import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.employee_module.dto.EmployeeRequest;
import com.varun.workforce_management.employee_module.dto.EmployeeResponseDTO;
import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.employee_module.repository.DesignationRepository;
import com.varun.workforce_management.employee_module.repository.EmployeeRepository;
import com.varun.workforce_management.exception.UserExistsException;
import lombok.Data;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Data
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DesignationRepository designationRepository;

    @Override
    public EmployeeResponseDTO addEmployee(EmployeeRequest employeeRequest, User user) {

        if (!user.getEmail().equals(employeeRequest.userEmail()))
            throw new UsernameNotFoundException("Invalid email: You can only create an employee profile for yourself.");

        if (employeeRepository.existsByPhoneNumber(employeeRequest.phoneNumber()))
            throw new UserExistsException("Phone Number already exists try another number");

        Employee employee = new Employee();
        employee.setUser(user);
        employee.setFirstName(employeeRequest.firstName());
        employee.setLastName(employeeRequest.lastName());
        employee.setPhoneNumber(employeeRequest.phoneNumber());
        employee.setAddress(employeeRequest.address());
        employee.setPermanentAddress(employeeRequest.permanentAddress());
        employee.setDesignation(designationRepository.findByDesignationName(employeeRequest.designationName()));
        employee.setDateOfJoining(employeeRequest.dateOfJoining());
        employee.setDateOfBirth(employeeRequest.dateOfBirth());
        employee.setGender(employeeRequest.gender());
        employee.setPanNumber(employeeRequest.panNumber());
        employee.setAadharNumber(employeeRequest.aadharNumber());
        employee.setBankAccountNumber(employeeRequest.bankAccountNumber());
        employee.setBankName(employeeRequest.bankName());
        employee.setIfscCode(employeeRequest.ifscCode());
        employee.setBranch(employeeRequest.branch());
        employee.setSalary(employeeRequest.salary());
        employee.setCreatedAt(Instant.now());
        employee.setUpdatedAt(Instant.now());

        Employee savedEmployee = employeeRepository.save(employee);

        return mapToDTO(savedEmployee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    private EmployeeResponseDTO mapToDTO(Employee employee) {
        return new EmployeeResponseDTO(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getUser().getEmail(),
                employee.getPhoneNumber(),
                employee.getDateOfBirth(),
                employee.getAddress(),
                employee.getPermanentAddress(),
                employee.getDateOfJoining(),
                employee.getDesignation() != null ? employee.getDesignation().getDesignationName() : null,
                employee.getGender(),
                employee.getPanNumber(),
                employee.getAadharNumber(),
                employee.getBankAccountNumber(),
                employee.getBankName(),
                employee.getIfscCode(),
                employee.getBranch(),
                employee.getSalary(),
                employee.getCreatedAt(),
                employee.getUpdatedAt());
    }
}
