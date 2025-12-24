package com.varun.workforce_management.employee_module.repository;

import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.employee_module.dto.EmployeeResponseDTO;
import com.varun.workforce_management.employee_module.entity.Employee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByPhoneNumber(String s);

    Employee findByUser(User user);

    Employee findByUser(Optional<User> byEmail);

    List<Employee> findByManagerId(Employee manager);

    Employee findByUser_Email(@Email(message = "Manager email must be valid") String s);
}
