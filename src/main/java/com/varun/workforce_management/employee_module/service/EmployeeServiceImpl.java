package com.varun.workforce_management.employee_module.service;


import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.auth_module.repository.UserRepository;
import com.varun.workforce_management.employee_module.dto.EmployeeRequest;
import com.varun.workforce_management.employee_module.dto.Message;
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
    private final UserRepository userRepository;



    @Override
    public Message addEmployee(EmployeeRequest employeeRequest) {
        User user = userRepository.findByEmail(employeeRequest.userEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email"));

        if (employeeRepository.findByPhoneNumber(employeeRequest.phoneNumber()))
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
        employeeRepository.save(employee);

        return new Message("Employee added successfully");
    }

    @Override
    public List<Message> getAllEmployees() {
        return List.of();
    }
}
