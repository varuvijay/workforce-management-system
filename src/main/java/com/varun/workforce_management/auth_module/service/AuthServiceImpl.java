package com.varun.workforce_management.auth_module.service;

import com.varun.workforce_management.auth_module.dto.*;
import com.varun.workforce_management.auth_module.entity.RefreshToken;
import com.varun.workforce_management.auth_module.entity.Role;
import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.auth_module.repository.RoleRepository;
import com.varun.workforce_management.auth_module.repository.UserRepository;
import com.varun.workforce_management.exception.TokenRefreshException;
import com.varun.workforce_management.exception.UserExistsException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public RegisterResponse registerUser(RegistrationRequest registrationRequest) {

        if (userRepository.existsByEmail(registrationRequest.getEmail()))
            throw new UserExistsException("User already exists");

        if (!roleRepository.existsByRoleName(registrationRequest.getRole().toUpperCase()))
            throw new UserExistsException("Role does not exist");

        registrationRequest.setPassword(passwordService.encodePassword(registrationRequest.getPassword()));

        Role role = roleRepository.findByRoleName(registrationRequest.getRole().toUpperCase())
                .orElseThrow(() -> new UserExistsException("Role not found"));

        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(registrationRequest.getPassword());
        user.setIsActive(true);
        user.setRole(role);

        userRepository.save(user);

        return new RegisterResponse("User registered successfully");
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
            String token = jwtService.generateToken(loginRequest.getEmail(), role);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());
            return new LoginResponse(token, refreshToken.getRefreshToken());
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> {
                    RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(refreshToken);
                    String role = newRefreshToken.getUser().getRole().getRoleName();
                    String accessToken = jwtService.generateToken(newRefreshToken.getUser().getEmail(), role);
                    return new RefreshTokenResponse(accessToken);
                })
                .orElseThrow(() -> new TokenRefreshException(request.getRefreshToken(), "Refresh token is not in database!"));
    }

    @Override
    public LogoutResponse logoutUser(RefreshTokenRequest refreshToken) {
        return refreshTokenService.findByToken(refreshToken.getRefreshToken())
                .map(refreshToken1 -> {
                    refreshTokenService.deleteToken(refreshToken1.getTokenId());
                    return new LogoutResponse("Logout successfully ");
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken.getRefreshToken(), "Refresh token is not in database!"));

    }
}
