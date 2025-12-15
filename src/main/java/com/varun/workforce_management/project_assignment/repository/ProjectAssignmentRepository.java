package com.varun.workforce_management.project_assignment.repository;

import com.varun.workforce_management.project_assignment.entity.ProjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Long> {
    org.springframework.data.domain. Page<ProjectAssignment> findByEmployee(
            com.varun.workforce_management.employee_module.entity.Employee employee,
            org.springframework.data.domain.Pageable pageable);
}
