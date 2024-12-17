package com.example.cafe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    //to order form menu 
    //getting menu options by customer
    // Get all MenuItems
    @GetMapping("/getAllMenuItem/by/customer")
    public String getAllMenuItems(Model model, @ModelAttribute("user") Users user) throws Exception {
        if (user == null) {
            return "redirect:/login"; // Redirect to login if the user is null
        }

        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        model.addAttribute("menuItems", menuItems);
        return "customer/customer-menu-list";
    }

    
    @PostMapping("/order/by/customer/{userId}")
    public String createOrder(@RequestParam("menuItemIds") List<Integer> menuItemIds,
                              @RequestParam("quantities") List<Integer> quantities,
                              @PathVariable("userId") Integer userId, 
                              Model model) throws Exception {
        if (menuItemIds.size() != quantities.size()) {
            throw new RuntimeException("Mismatch between menu items and quantities");
        }

        // Construct `OrderItemDTO` data
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        for (int i = 0; i < menuItemIds.size(); i++) {
            if (quantities.get(i) > 0) { // Only add items with a quantity > 0
                OrderItemDTO dto = new OrderItemDTO();
                dto.setMenuItemId(menuItemIds.get(i));
                dto.setQuantity(quantities.get(i));
                orderItemDTOs.add(dto);
            }
        }

        Orders order = orderService.createOrder(orderItemDTOs, userId);
        model.addAttribute("order", order);

        return "customer/bill"; // Redirect to the bill view
    }

    
}

