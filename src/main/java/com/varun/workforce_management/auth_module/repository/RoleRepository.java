package com.varun.workforce_management.auth_module.repository;

import com.varun.workforce_management.auth_module.entity.Role;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRoleName(@NotBlank(message = "Role cannot be blank") String role);

    Optional<Role> findByRoleName(String role);
}
