package com.example.cafe.service;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cafe.entity.Users;
import com.example.cafe.repository.UsersRepository;

@Service
public class UserService {

	@Autowired
	private UsersRepository userRepository; 
	

	
	@Autowired
	PasswordEncoder passwordEncoder;
	public Users addNewUser(Users userdto) throws Exception {
		
		if(userdto==null)throw new Exception("usern is empty cant register");
		
		Users user = new Users();
		user.setEmail(userdto.getEmail());
		user.setFullName(userdto.getFullName());
		user.setRole(userdto.getRole());
		user.setUsername(userdto.getFullName());
		user.setPassword(passwordEncoder.encode(userdto.getPassword()));
		
		return userRepository.save(user);
	}


	public Users findByUserEmail(String email) {
		return userRepository.findByEmail(email);
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

}
