package com.example.cafe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cafe.DTO.UserDto;
import com.example.cafe.entity.Users;
import com.example.cafe.repository.UsersRepository;

@Service
public class UserService {

	@Autowired
	private UsersRepository userRepository; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    public Users addNewUser(UserDto userDto) throws Exception {
        if (userDto == null) {
            throw new Exception("User is empty. Cannot register.");
        }

        // Check if email already exists
        if (emailExists(userDto.getEmail())) {
            throw new Exception("Email already exists.");
        }

        Users user = new Users();
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        
        return userRepository.save(user);
    }


	public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
	

	
	 public Users authenticate(String username, String password) {
	        // Fetch the user by username from the database
	        Users user = userRepository.findByUsername(username);

	        // Check if user exists and the password matches
	        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
	            return user;  // Return the authenticated user
	        }

	        // If authentication fails, return null
	        return null;
	    }


	 public List<Users> getAllUsers() {
	        return userRepository.findAll(); // Retrieve all users
	    }


	 public void deleteUserById(Long userId) {
	        if (!userRepository.existsById(userId)) {
	            throw new RuntimeException("User with ID " + userId + " does not exist.");
	        }
	        userRepository.deleteById(userId); // Delete the user
	    }


	public Users getUserById(Long id) {
		return userRepository.findByUserId(id); 
	}

}
