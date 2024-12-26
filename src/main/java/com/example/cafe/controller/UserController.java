package com.example.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cafe.DTO.UserDto;
import com.example.cafe.entity.Users;
import com.example.cafe.enums.Role;
import com.example.cafe.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	
	 	@GetMapping("/admin/register/new-user")
	    public String showRegistrationForm(Model model) {
		 
	        model.addAttribute("userDto", new UserDto());
	        return "register-form";
	    }

	    @PostMapping("/register")
	    public String registerUser(@ModelAttribute("userDto") UserDto userDto, Model model) throws Exception {
	        // Check if the email already exists
	        if (userService.emailExists(userDto.getEmail())) {
	            model.addAttribute("errorMessage", "Email already exists. Please use a different email.");
	            return "register-form"; // Redirect back to registration form with error
	        }

	        // Check if the passwords match
	        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
	            model.addAttribute("errorMessage", "Passwords do not match. Please try again.");
	            return "register-form"; // Redirect back to registration form with error
	        }

	        // If validation passes, proceed to create the user
	        try {
	            userService.addNewUser(userDto);
	        } catch (Exception e) {
	            model.addAttribute("errorMessage", e.getMessage());
	            return "register-form"; // Redirect back to registration form with error
	        }

	        return "redirect:/login";  // Redirect to login page after successful registration
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
	public String showHomePage(@ModelAttribute("user") Users user) {

	    if (user != null) {
	        System.out.println(user);
	        System.out.println(user.getEmail());
	        
	        // Check the user's role and redirect accordingly
	        if (user.getRole() != null) {
	            if (user.getRole()== Role.ADMIN) {
	                return "dashboard"; // Redirect to admin dashboard
	            } else if (user.getRole()== Role.CUSTOMER) {
	                return "redirect:/customer/getAllMenuItem"; // Redirect to customer menu
	            }
	        }
	    }

	    // Default redirect in case no user or role is found
	    return "redirect:/login"; // Redirect to login if no user is authenticated
	}

	
	//display all users 
	@GetMapping("/admin/view/all/users")
    public String viewAllUsers(Model model) {
        // Retrieve all users from the database
        List<Users> userList = userService.getAllUsers();
        
        model.addAttribute("users", userList);
        
        return "admin-user-list";
    }

	
	 @GetMapping("/admin/user/delete/{id}")
	    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		 
	        Users user = userService.getUserById(id);
	        if (user == null) {
	            redirectAttributes.addFlashAttribute("errorMessage", "User not found.");
	        } else if (user.getRole() == Role.ADMIN) {
	            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete an admin user.");
	        } else {
	            userService.deleteUserById(id);
	            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
	        }
	        return "redirect:/admin/view/all/users";
	    }
	
}
