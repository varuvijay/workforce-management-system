package com.varun.workforce_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection for stateless APIs
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth ->
                auth
                    // Permit all requests to the authentication endpoints
                    .requestMatchers("/api/v1/auth/**")
                    .permitAll()
                    // All other requests must be authenticated
                    .anyRequest()
                    .authenticated()
            )
            // Configure session management to be stateless
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
        //        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User
            .withUsername("varun")
            .password(passwordEncoder().encode("varun"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}
