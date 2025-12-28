package com.varun.workforce_management.leave_module.repository;

import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.leave_module.entity.LeaveRequest;
import com.varun.workforce_management.leave_module.entity.LeaveStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployeeOrderByStartDateDesc(Employee employee);

    boolean existsByEmployeeAndStatusInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Employee employee,
            List<LeaveStatus> statuses,
            LocalDate endDate,
            LocalDate startDate);

    // For manager: pending leaves
    // "View pending leaves of team". Team means direct reports? Or all?
    // User requirement: "View pending leaves of team". implies `employee.manager =
    // currentUser`.
    // But later API says: `getPendingLeavesForManager`.
    // Let's add: findByEmployee_ManagerIdAndStatus(Employee manager, LeaveStatus
    // status);
    List<LeaveRequest> findByEmployee_ManagerIdAndStatusOrderByAppliedAtDesc(Employee manager, LeaveStatus status);
}
