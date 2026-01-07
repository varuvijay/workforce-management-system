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
import com.varun.workforce_management.exception.UserExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DesignationRepository designationRepository;
    private final UserRepository userRepository;

    @Override
    @PreAuthorize("hasRole('HR')")
    @Transactional
    public EmployeeResponseDTO addEmployee(EmployeeCreateRequest request) {
        log.info("Adding new employee with email: {}", request.userEmail());

        User user = getUserByEmail(request.userEmail());
        validatePhoneNumberDoesNotExist(request.phoneNumber());
        Designation designation = getDesignationByName(request.designationName());
        Employee manager = getManagerByEmail(request.managerEmail());

        Employee employee = Employee.create(user, request, designation);
        if (manager != null) {
            employee.setManagerId(manager);
        }

        return saveAndMapToDto(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees(int page, int size) {
        log.debug("Fetching all employees page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable).stream()
                .map(EmployeeResponseDTO::from)
                .toList();
    }

    @Override
    public EmployeeResponseDTO getEmployeeByEmail(String email) {
        log.debug("Fetching employee details for email: {}", email);
        return userRepository.findByEmail(email)
                .map(employeeRepository::findByUser)
                .map(EmployeeResponseDTO::from)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for email: " + email));
    }

    @Override
    @PreAuthorize("hasRole('HR')")
    @Transactional
    public EmployeeResponseDTO updateEmployee(EmployeeCreateRequest request, String email) {
        log.info("Updating employee with email: {}", email);

        Employee existingEmployee = getEmployeeEntityByEmail(email);

        User user = getUserByEmail(request.userEmail());
        Designation designation = getDesignationByName(request.designationName());
        Employee manager = getManagerByEmail(request.managerEmail());

        Employee updatedEmployee = Employee.update(user, request, designation, existingEmployee.getEmployeeId());

        if (manager != null) {
            updatedEmployee.setManagerId(manager);
        }

        return saveAndMapToDto(updatedEmployee);
    }

    @Override
    @Transactional
    public Message deleteEmployee(String email) {
        log.info("Deleting employee with email: {}", email);
        Employee employee = getEmployeeEntityByEmail(email);
        employeeRepository.delete(employee);
        return new Message("Employee deleted successfully");
    }



    
    // --- Private Helper Methods ---

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for email: " + email));
    }

    private Designation getDesignationByName(String name) {
        Designation designation = designationRepository.findByDesignationName(name);
        if (designation == null) {
            throw new ResourceNotFoundException("Designation not found: " + name);
        }
        return designation;
    }

    private Employee getManagerByEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }
        Employee manager = employeeRepository.findByUser_Email(email);
        if (manager == null) {
            throw new ResourceNotFoundException("Manager not found for email: " + email);
        }
        return manager;
    }

    private Employee getEmployeeEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(employeeRepository::findByUser)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for email: " + email));
    }

    private void validatePhoneNumberDoesNotExist(String phoneNumber) {
        if (employeeRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UserExistsException("Phone Number already exists try another number");
        }
    }

    
    private EmployeeResponseDTO saveAndMapToDto(Employee employee) {
        try {
            Employee savedEmployee = employeeRepository.save(employee);
            return EmployeeResponseDTO.from(savedEmployee);
        } catch (DataIntegrityViolationException ex) {
            String msg = ex.getMostSpecificCause().getMessage();
            if (msg != null && msg.contains("Detail:")) {
                msg = msg.substring(msg.indexOf("Detail:") + 8);
            }
            throw new UserExistsException(msg != null ? msg : "Database error occurred");
        }
    }
}
