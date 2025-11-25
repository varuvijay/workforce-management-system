package com.varun.workforce_management.employee_module.repository;

import com.varun.workforce_management.employee_module.entity.Designation;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {


    Designation findByDesignationName(@NotBlank(message = "Designation name cannot be blank") String s);
}
