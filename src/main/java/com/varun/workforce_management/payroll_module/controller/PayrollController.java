package com.varun.workforce_management.payroll_module.controller;

import com.varun.workforce_management.payroll_module.dto.PayrollDTO;
import com.varun.workforce_management.payroll_module.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payrolls")
@RequiredArgsConstructor
public class PayrollController {

    
}
