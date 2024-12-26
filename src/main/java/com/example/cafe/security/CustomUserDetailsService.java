package com.example.cafe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cafe.entity.Users;
import com.example.cafe.repository.UsersRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch the user by email
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));


        // Build and return Spring Security's UserDetails object
        return User.withUsername(user.getEmail())
                   .password(user.getPassword())
                   .roles(user.getRole().name()) // Assuming roles are enum values
                   .build();
    }
}
