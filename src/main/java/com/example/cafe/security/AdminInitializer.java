package com.example.cafe.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.cafe.entity.Users;
import com.example.cafe.enums.Role;
import com.example.cafe.repository.UsersRepository;


@Configuration
public class AdminInitializer {
	
    @Bean
    public CommandLineRunner initAdmin(UsersRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin";
            String adminPassword = "123";
            // Check if the admin username exists in the repository
           // Optional<Users> existingAdmin = userRepository.findByEmail(adminUsername);
            	
            if(userRepository.findByEmail(adminEmail).isEmpty()) {
            	 Users admin = new Users();
               admin.setEmail(adminEmail);
               admin.setUsername(adminEmail);
               admin.setPassword(passwordEncoder.encode(adminPassword)); // Password encoding
               admin.setRole(Role.ADMIN);  // Assign role as Admin
               userRepository.save(admin);  // Save to the database
            }else {
            	System.out.println("admin already exists");
            }
            
//            if (existingAdmin == null) {  // If no admin is found, create one
//                Users admin = new Users();
//                admin.setEmail(adminUsername);
//                admin.setPassword(passwordEncoder.encode(adminPassword)); // Password encoding
//                admin.setRole(Role.ADMIN);  // Assign role as Admin
//                userRepository.save(admin);  // Save to the database
//            }
        };
    }
}
