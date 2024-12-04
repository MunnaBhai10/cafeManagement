package com.example.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.cafe.service.OrderdetailsService;

@Controller
public class OrderdetailsController {

    @Autowired
    private OrderdetailsService orderdetailsService;

    // View order details for a specific order
    @GetMapping("/orders/{orderId}/details")
    public String viewOrderDetails(@PathVariable Integer orderId, Model model) {
        model.addAttribute("orderDetails", orderdetailsService.getOrderDetailsByOrderId(orderId));
        return "order-details";  // This will return the order-details.html template
    }

    // View all order details
    @GetMapping("/orderdetails")
    public String viewAllOrderDetails(Model model) {
        model.addAttribute("orderDetails", orderdetailsService.getAllOrderDetails());
        return "orderdetails";  // This will return the orderdetails.html template
    }
}
