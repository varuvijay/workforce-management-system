package com.varun.workforce_management.auth_module.service;

import com.varun.workforce_management.auth_module.dto.UserPrincipal;
import com.varun.workforce_management.auth_module.entity.User;
import com.varun.workforce_management.auth_module.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);

        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found");


        return new UserPrincipal(user.get());
    }
}
