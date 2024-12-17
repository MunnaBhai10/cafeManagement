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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Store the Users entity in the session
        httpSession.setAttribute("loggedInUser", user);

        // Return Spring Security's UserDetails object
        return User.withUsername(user.getEmail())
                   .password(user.getPassword())
                   .roles(user.getRole().name()) // Assuming roles are enum values
                   .build();
    }
}
