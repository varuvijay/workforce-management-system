package com.varun.workforce_management.auth_module.dto;

import com.varun.workforce_management.auth_module.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserPrincipal implements UserDetails {

    private User user;

    public UserPrincipal(User user) {
        // Fail fast: Ensure the user and critical fields are present
        this.user = Objects.requireNonNull(user, "User entity must not be null");


        Objects.requireNonNull(user.getEmail(), "Email cannot be null");
        Objects.requireNonNull(user.getPassword(), "Password cannot be null");
        Objects.requireNonNull(user.getRole(), "User role must not be null");
        Objects.requireNonNull(user.getRole().getRoleName(), "Role name cannot be null");
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Spring Security's hasRole() expects "ROLE_" prefix
        return List.of(() -> "ROLE_" + user.getRole().getRoleName());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
