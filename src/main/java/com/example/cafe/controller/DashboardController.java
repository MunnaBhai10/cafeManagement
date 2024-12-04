package com.example.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.cafe.service.DashboardService;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Fetch dynamic data for the dashboard
        model.addAttribute("totalUsers", dashboardService.getTotalUsers());
        model.addAttribute("totalOrders", dashboardService.getTotalOrders());
        model.addAttribute("activeTables", dashboardService.getActiveTables());
        model.addAttribute("pendingBills", dashboardService.getPendingBills());
        return "dashboard";
    }
    
   @GetMapping("/userDashboard")
   public String showUserDashboard(Model model) {
	   return "userDashboard";
   }
}
