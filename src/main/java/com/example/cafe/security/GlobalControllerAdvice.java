package com.example.cafe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.cafe.entity.Users;
import com.example.cafe.repository.UsersRepository;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UsersRepository usersRepository;  // Injecting UsersRepository to fetch user details
    
    @ModelAttribute("user")
    public Users addGlobalAttributes(Model model) {
        // Retrieve the authenticated user using SecurityContextHolder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (principal instanceof UserDetails) {
            // Extract the email (username) of the logged-in user
            String email = ((UserDetails) principal).getUsername();
            System.out.println("Logged in user email: " + email);
            
            // Fetch the full user entity from the database using the email
            Users user = usersRepository.findByEmail(email);
            
            if (user != null) {
                System.out.println("Retrieved user from database: " + user.getEmail());
                return user;  // Return the user object to be used in all controllers
            } else {
                System.out.println("User not found in database.");
            }
        }
        
        // If the user is not logged in or doesn't exist in the database, return null or handle as needed
        return null;
    }
}