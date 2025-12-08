package com.varun.workforce_management.employee_module.service;

import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.auth_module.repository.UserRepository;
import com.varun.workforce_management.employee_module.dto.EmployeeCreateRequest;
import com.varun.workforce_management.employee_module.dto.EmployeeResponseDTO;
import com.varun.workforce_management.employee_module.dto.Message;
import com.varun.workforce_management.employee_module.entity.Designation;
import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.employee_module.repository.DesignationRepository;
import com.varun.workforce_management.employee_module.repository.EmployeeRepository;
import com.varun.workforce_management.exception.ResourceNotFoundException;
import com.varun.workforce_management.exception.UnauthorizedAccessException;
import com.varun.workforce_management.exception.UserExistsException;

import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DesignationRepository designationRepository;
    private final UserRepository userRepository;

    @Override
    @PreAuthorize("hasRole('HR')")
    public EmployeeResponseDTO addEmployee(EmployeeCreateRequest employeeCreateRequest) {

            if (employeeRepository.existsByPhoneNumber(employeeCreateRequest.phoneNumber()))
                throw new UserExistsException("Phone Number already exists try another number");

            Designation designation = designationRepository.findByDesignationName(employeeCreateRequest.designationName());
            if (designation == null) {
                throw new ResourceNotFoundException("Designation not found: " + employeeCreateRequest.designationName());
            }

            User user = userRepository.findByEmail(employeeCreateRequest.email());
            if (user == null) {
                throw new ResourceNotFoundException("User not found for email: " + employeeCreateRequest.email());
            }

            Employee employee = Employee.create(user, employeeCreateRequest, designation);

            try {
                return EmployeeResponseDTO.from(employeeRepository.save(employee));
            } catch (DataIntegrityViolationException ex) {
                String msg = ex.getMostSpecificCause().getMessage();
                if (msg.contains("Detail:")) {
                    msg = msg.substring(msg.indexOf("Detail:") + 8);
                }
                throw new UserExistsException(msg);
            }

    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeResponseDTO::from)
                .toList();
    }

    @Override
    public EmployeeResponseDTO getEmployeeByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> employeeRepository.findByUser(user))
                .map(EmployeeResponseDTO::from)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Override
    @PreAuthorize("hasRole('HR')")
    public EmployeeResponseDTO updateEmployee(EmployeeCreateRequest employeeCreateRequest, String email) {
        
        Employee employee = employeeRepository.findByUser(userRepository.findByEmail(email));    
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found for email: " + email);
        }

        if (employeeRepository.existsByPhoneNumber(employeeCreateRequest.phoneNumber()))
                throw new UserExistsException("Phone Number already exists try another number");

            Designation designation = designationRepository.findByDesignationName(employeeCreateRequest.designationName());
            if (designation == null) {
                throw new ResourceNotFoundException(STR."Designation not found: \{employeeCreateRequest.designationName()}");
            }

            Employee employee = Employee.create(user, employeeCreateRequest, designation);

            try {
                return EmployeeResponseDTO.from(employeeRepository.save(empl oyee));
            } catch (DataIntegrityViolationException ex) {
                String msg = ex.getMostSpecificCause().getMessage();
                if (msg.contains("Detail:")) {
                    msg = msg.substring(msg.indexOf("Detail:") + 8);
                }
                throw new UserExistsException(msg);
            }

    }

    @Override
    public Message deleteEmployee(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for email: " + email));

        Employee employee = employeeRepository.findByUser(user);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee profile not found for email: " + email);
        }

        employeeRepository.delete(employee);
        return new Message("Employee deleted successfully");
    }

}
