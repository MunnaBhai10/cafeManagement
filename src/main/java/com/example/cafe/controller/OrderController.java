package com.example.cafe.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cafe.DTO.OrderItemDTO;
import com.example.cafe.entity.MenuItem;
import com.example.cafe.entity.Orders;
import com.example.cafe.entity.Users;
import com.example.cafe.service.MenuItemService;
import com.example.cafe.service.OrderService;

@Controller
@RequestMapping
public class OrderController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private OrderService orderService;

    // Get all menu items for the customer
    @GetMapping("/customer/getAllMenuItem")
    public String getAllMenuItems(Model model, @ModelAttribute("user") Users user) throws Exception {
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is null
        }

        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        model.addAttribute("menuItems", menuItems);
        return "customer/customer-menu-list";
    }

    // Create an order by the customer
    @PostMapping("/customer/order/by/{userId}")
    public String createOrder(@RequestParam("menuItemIds") List<Integer> menuItemIds,
                              @RequestParam("quantities") List<Integer> quantities,
                              @PathVariable("userId") Long userId,
                              RedirectAttributes redirectAttributes) throws Exception {

        if (menuItemIds.size() != quantities.size()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Mismatch between menu items and quantities.");
            return "redirect:/customer/getAllMenuItem";
        }

        boolean allQuantitiesZero = quantities.stream().allMatch(q -> q <= 0);
        if (allQuantitiesZero) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot create an order with all quantities as zero.");
            return "redirect:/customer/getAllMenuItem";
        }

        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        for (int i = 0; i < menuItemIds.size(); i++) {
            if (quantities.get(i) > 0) {
                OrderItemDTO dto = new OrderItemDTO();
                MenuItem menuItem = menuItemService.getMenuItemById(menuItemIds.get(i));
                dto.setMenuItemId(menuItemIds.get(i));
                dto.setQuantity(quantities.get(i));
                dto.setItemName(menuItem.getItemName());
                dto.setItemPrice(menuItem.getItemPrice());
                orderItemDTOs.add(dto);
            }
        }

        Orders order = orderService.createOrder(orderItemDTOs, userId);
        return "redirect:/customer/order/bill/" + order.getOrderId();
    }

    // Display the order bill
    @GetMapping("/customer/order/bill/{orderId}")
    public String showBill(@PathVariable("orderId") Long orderId, Model model) throws Exception {
        Orders order = orderService.getOrderById(orderId);
        List<OrderItemDTO> orderItems = orderService.getOrderItemsByOrderId(orderId);

        model.addAttribute("order", order);
        model.addAttribute("orderItems", orderItems);
        return "customer/bill";
    }

    // Cancel an order by customer
    @PostMapping("/customer/order/cancel/{orderId}")
    public String cancelOrder(@PathVariable("orderId") Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderService.cancelOrder(orderId);
            redirectAttributes.addFlashAttribute("successMessage", "Order cancelled successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/customer/getAllMenuItem";
    }

    // Pay for an order by customer
    @PostMapping("/customer/pay/order/{orderId}")
    public String payOrder(@PathVariable("orderId") Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderService.payOrder(orderId);
            redirectAttributes.addFlashAttribute("successMessage", "Payment successful!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/customer/order/bill/" + orderId;
    }

    // Mark order as received by customer
    @PostMapping("/customer/order/{orderId}/received")
    public String markOrderAsReceived(@PathVariable("orderId") Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderService.markAsReceived(orderId);
            redirectAttributes.addFlashAttribute("successMessage", "Thank you! Your order is successfully completed.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/customer/order/bill/" + orderId;
    }
}
