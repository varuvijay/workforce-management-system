package com.varun.workforce_management.payroll_module.repository;

import com.varun.workforce_management.payroll_module.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
}
