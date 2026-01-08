package com.varun.workforce_management.performance_module.repository;

import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.performance_module.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findByEmployee(Employee employee);

    List<Performance> findByManager(Employee manager);
}
