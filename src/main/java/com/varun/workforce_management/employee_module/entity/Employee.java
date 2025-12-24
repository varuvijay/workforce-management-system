package com.varun.workforce_management.employee_module.entity;

import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.employee_module.dto.EmployeeCreateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Employee managerId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "permanent_address")
    private String permanentAddress;

    @Column(name = "date_of_joining", nullable = false)
    private LocalDate dateOfJoining;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "designation_id")
    private Designation designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

    @Column(name = "pan_number", length = 10, unique = true)
    private String panNumber;

    @Column(name = "aadhar_number", length = 12, unique = true)
    private String aadharNumber;

    @Column(name = "bank_account_number", length = 20)
    private String bankAccountNumber;

    @Column(name = "bank_name", length = 100)
    private String bankName;

    @Column(name = "ifsc_code", length = 11)
    private String ifscCode;

    @Column(name = "branch", length = 100)
    private String branch;

    @Column(name = "salary")
    private java.math.BigDecimal salary;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    public static Employee create(User user, EmployeeCreateRequest request, Designation designation) {
        Employee employee = new Employee();
        employee.setUser(user);
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setPhoneNumber(request.phoneNumber());
        employee.setAddress(request.address());
        employee.setPermanentAddress(request.permanentAddress());
        employee.setDesignation(designation);
        employee.setDateOfJoining(request.dateOfJoining());
        employee.setDateOfBirth(request.dateOfBirth());
        employee.setGender(request.gender());
        employee.setPanNumber(request.panNumber());
        employee.setAadharNumber(request.aadharNumber());
        employee.setBankAccountNumber(request.bankAccountNumber());
        employee.setBankName(request.bankName());
        employee.setIfscCode(request.ifscCode());
        employee.setBranch(request.branch());
        employee.setSalary(request.salary());
        return employee;
    }

       public static Employee update(User user, EmployeeCreateRequest request, Designation designation,  Long employeeId) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setUser(user);
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setPhoneNumber(request.phoneNumber());
        employee.setAddress(request.address());
        employee.setPermanentAddress(request.permanentAddress());
        employee.setDesignation(designation);
        employee.setDateOfJoining(request.dateOfJoining());
        employee.setDateOfBirth(request.dateOfBirth());
        employee.setGender(request.gender());
        employee.setPanNumber(request.panNumber());
        employee.setAadharNumber(request.aadharNumber());
        employee.setBankAccountNumber(request.bankAccountNumber());
        employee.setBankName(request.bankName());
        employee.setIfscCode(request.ifscCode());
        employee.setBranch(request.branch());
        employee.setSalary(request.salary());
        return employee;
    }
}
