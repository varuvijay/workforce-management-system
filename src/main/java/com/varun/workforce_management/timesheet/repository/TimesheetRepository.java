package com.varun.workforce_management.timesheet.repository;

import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.timesheet.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    List<Timesheet> findByEmployeeInAndWorkDate(List<Employee> employees, LocalDate workDate);

    Optional<Timesheet> findByEmployeeAndWorkDate(Employee employee, LocalDate workDate);

}
