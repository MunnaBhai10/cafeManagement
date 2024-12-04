package com.example.cafe.controller;

import com.example.cafe.entity.Users;
import com.example.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/new")
    public String createUserForm(Model model) {
    	model.addAttribute("user", new Users());
    	return "user-form";
    }
    
    @GetMapping("/list")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";
    }

    
    
    
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") Users user) {
        userService.createUser(user);
        return "redirect:/users/list";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") Integer userId, Model model) {
        model.addAttribute("user", userService.getUserById(userId));
        return "user-form";
    }
}
