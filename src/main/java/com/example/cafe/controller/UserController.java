package com.example.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.cafe.entity.Users;
import com.example.cafe.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	
	// getMapping for register--------
	@GetMapping("/register")
	public String addNewUser(Model model) {
		Users user = new Users();
		model.addAttribute("user", user);
		return "register-form";
		
	}
	
	@PostMapping("/register")
	public String addNewUser(@ModelAttribute("user") Users user) throws Exception {
		userService.addNewUser(user);
		return "redirect:/login";
		
	}
	
	@GetMapping("/login")
	public String login() {
		return "loginForm";
		
	}
	
	@GetMapping("/logout")
    public String logout() {
        // Any custom logic before redirecting to the login page (optional)
        return "redirect:/login?logout"; 
    }
	
	@GetMapping("/")
	public String showHomePage(@ModelAttribute("user")Users user) {
		
		if(user != null) {
			System.out.println(user);
			//System.out.println(user.getUsername());
			System.out.println(user.getEmail());
		}

		return "Dashboard";
		
		
	}
	

}
