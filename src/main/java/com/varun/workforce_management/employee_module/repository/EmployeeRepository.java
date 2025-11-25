package com.varun.workforce_management.employee_module.repository;

import com.varun.workforce_management.employee_module.entity.Employee;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    boolean findByPhoneNumber( String s);

}
