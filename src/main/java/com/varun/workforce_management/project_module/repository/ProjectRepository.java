package com.varun.workforce_management.project_module.repository;

import com.varun.workforce_management.employee_module.entity.Employee;
import com.varun.workforce_management.project_module.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByProjectName(String projectName);

    List<Project> findByEmployeesContains(Employee employee);
}
