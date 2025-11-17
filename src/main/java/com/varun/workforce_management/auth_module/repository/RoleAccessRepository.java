package com.varun.workforce_management.auth_module.repository;

import com.varun.workforce_management.auth_module.entity.RoleAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAccessRepository extends JpaRepository<RoleAccess, Long> {

}
