package com.varun.workforce_management.employee_module.controller;

import com.varun.workforce_management.employee_module.dto.EmployeeRequest;
import com.varun.workforce_management.employee_module.dto.Message;
import com.varun.workforce_management.employee_module.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Data
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/")
    public ResponseEntity<Message> addEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.addEmployee(employeeRequest));
    }

    @GetMapping("/")
    public ResponseEntity<List<Message>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
}
