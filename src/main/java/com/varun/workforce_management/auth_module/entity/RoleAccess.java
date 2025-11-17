package com.varun.workforce_management.auth_module.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "role_access")
public class RoleAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "can_manage_employees")
    private Boolean canManageEmployees;

    @Column(name = "can_manage_projects")
    private Boolean canManageProjects;

    @Column(name = "can_approve_leaves")
    private Boolean canApproveLeaves;

    @Column(name = "can_manage_salary")
    private Boolean canManageSalary;
}
