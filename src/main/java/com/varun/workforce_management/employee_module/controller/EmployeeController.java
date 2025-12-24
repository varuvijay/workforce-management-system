package com.varun.workforce_management.employee_module.controller;

import com.varun.workforce_management.auth_module.dto.UserPrincipal;
import com.varun.workforce_management.employee_module.dto.EmployeeCreateRequest;
import com.varun.workforce_management.employee_module.dto.EmployeeResponseDTO;
import com.varun.workforce_management.employee_module.dto.Message;
import com.varun.workforce_management.employee_module.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/employees")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<EmployeeResponseDTO> addEmployee(
            @Valid @RequestBody EmployeeCreateRequest employeeCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.addEmployee(employeeCreateRequest));
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(employeeService.getAllEmployees(page, size));
    }

    @GetMapping("/{email}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeByEmail(@PathVariable @Email String email) {
        return ResponseEntity.ok(employeeService.getEmployeeByEmail(email));
    }

    @PutMapping("/{email}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @Valid @RequestBody EmployeeCreateRequest employeeCreateRequest,
            @PathVariable @Email String email) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.updateEmployee(employeeCreateRequest, email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Message> deleteEmployee(@PathVariable @Email String email) {
        return ResponseEntity.ok(employeeService.deleteEmployee(email));
    }

    @GetMapping("/me")
    public ResponseEntity<EmployeeResponseDTO> getUserDetails(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(employeeService.getEmployeeByEmail(userPrincipal.getUser().getEmail()));
    }

   
    

}
