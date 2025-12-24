package com.varun.workforce_management.helper;

import com.varun.workforce_management.auth_module.entity.Role;
import com.varun.workforce_management.auth_module.repository.RoleRepository;
import com.varun.workforce_management.employee_module.entity.Designation;
import com.varun.workforce_management.employee_module.repository.DesignationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final DesignationRepository designationRepository;

    @Override
    public void run(String... args) throws Exception {
        seedRoles();
        seedDesignations();
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {
            log.info("Seeding Roles...");
            List<Role> roles = List.of(
                    new Role(null, "ADMIN"),
                    new Role(null, "MANAGER"),
                    new Role(null, "EMPLOYEE"),
                    new Role(null, "HR"));
            roleRepository.saveAll(roles);
            log.info("Roles seeded successfully.");
        } else {
            log.info("Roles already exist. Skipping seeding.");
        }
    }

    private void seedDesignations() {
        if (designationRepository.count() == 0) {
            log.info("Seeding Designations...");
            List<String> designationNames = List.of(
                    "Junior Software Engineer",
                    "Software Engineer",
                    "Senior Software Engineer",
                    "Staff Software Engineer",
                    "Principal Software Engineer",
                    "Tech Lead",
                    "Engineering Manager",
                    "Senior Engineering Manager",
                    "Director of Engineering",
                    "VP of Engineering",
                    "CTO");

            List<Designation> designations = designationNames.stream()
                    .map(name -> {
                        Designation d = new Designation();
                        d.setDesignationName(name);
                        return d;
                    })
                    .toList();

            designationRepository.saveAll(designations);
            log.info("Designations seeded successfully.");
        } else {
            log.info("Designations already exist. Skipping seeding.");
        }
    }
}
