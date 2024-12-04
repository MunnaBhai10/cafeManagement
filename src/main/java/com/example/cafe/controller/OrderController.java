package com.example.cafe.controller;

import com.example.cafe.entity.Orders;
import com.example.cafe.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order-list";
    }

    @GetMapping("/new")
    public String createOrderForm(Model model) {
        model.addAttribute("order", new Orders());
        return "order-form";
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute("order") Orders order) {
        orderService.createOrder(order);
        return "redirect:/orders/list";
    }

    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable("id") Integer orderId, Model model) {
        model.addAttribute("order", orderService.getOrderById(orderId));
        return "order-form";
    }
}
