package com.varun.workforce_management.leave_module.repository;

import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.leave_module.entity.LeaveRequest;
import com.varun.workforce_management.leave_module.entity.LeaveStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeOrderByStartDateDesc(Employee employee);

    // Check for overlapping leaves:
    @Query("SELECT COUNT(l) > 0 FROM LeaveRequest l WHERE l.employee = :employee AND l.status IN :statuses AND l.endDate >= :startDate AND l.startDate <= :endDate")
    boolean existsOverlappingLeave(
            @Param("employee") Employee employee,
            @Param("statuses") List<LeaveStatus> statuses,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<LeaveRequest> findByEmployee_ManagerIdAndStatusOrderByAppliedAtDesc(Employee manager, LeaveStatus status);
}
