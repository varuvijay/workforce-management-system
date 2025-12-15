package com.varun.workforce_management.timesheet.repository;

import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.timesheet.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    Timesheet findByEmployeeAndWorkDate(Employee employee, LocalDate workDate);

}
