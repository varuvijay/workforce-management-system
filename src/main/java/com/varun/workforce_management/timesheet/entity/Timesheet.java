package com.varun.workforce_management.timesheet.entity;

import com.varun.workforce_management.employee_module.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "timesheets")
@Data
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @CreationTimestamp
    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "checked_in", nullable = false)
    private Instant checkedIn;

    @Column(name = "checked_out")
    private Instant checkedOut;

    @Column(name = "task_description", length = 2000)
    private String taskDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimesheetStatus status = TimesheetStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private Employee approvedBy;

    @Column(name = "approved_at")
    private Instant approvedAt;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;


    // updated checked in time
    public static Timesheet createCheckIn(Employee employee) {
        Timesheet timesheet = new Timesheet();
        timesheet.setEmployee(employee);
        timesheet.setWorkDate(LocalDate.now());
        timesheet.setCheckedIn(Instant.now());
        timesheet.setStatus(TimesheetStatus.PENDING);
        return timesheet;
    }
}
