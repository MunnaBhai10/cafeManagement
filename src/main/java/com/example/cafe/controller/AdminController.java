package com.example.cafe.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cafe.entity.MenuItem;
import com.example.cafe.entity.Orders;
import com.example.cafe.service.MenuItemService;
import com.example.cafe.service.OrderService;


@Controller
public class AdminController {

	@Autowired	
	private MenuItemService menuItemService;

	@Autowired	
	private OrderService orderService;

	//display all orders
	// Display all orders except Completed or Cancelled
	@GetMapping("/admin/view/all/orders")
	public String getActiveOrders(Model model) {
	    // Retrieve all orders
	    List<Orders> allOrders = orderService.getAllOrders();
	    
	    // Filter out orders with status "Completed" or "Cancelled"
	    List<Orders> activeOrders = allOrders.stream()
	            .filter(order -> !order.getOrderStatus().equalsIgnoreCase("Completed") &&
	                             !order.getOrderStatus().equalsIgnoreCase("Canceled"))
	            .toList();

	    // Add filtered orders to the model
	    model.addAttribute("orders", activeOrders);
	    return "admin-orders"; // Return Thymeleaf template name
	}

	
	@GetMapping("/admin/view/orders/24hr")
	public String getAllOrdersToday(Model model) {
	    LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
	    
	    // Query only necessary data
	    List<Orders> ordersLast24Hours = orderService.getOrdersInLast24Hours(twentyFourHoursAgo);

	    model.addAttribute("orders", ordersLast24Hours); // Add filtered orders to the model
	    model.addAttribute("onlyView",ordersLast24Hours);
	    return "admin-orders"; // Return Thymeleaf template
	}


	// for admin to update the orderStatus to "order-sent
	//order-sent 
    @PostMapping("/admin/order/{orderId}/update-status")
    public String updateOrderStatusByAdmin(
            @PathVariable("orderId") Long orderId,
            @RequestParam("status") String status,
            RedirectAttributes redirectAttributes) {

        try {
            // Call service to update order status
            orderService.updateOrderStatus(orderId, status);
            redirectAttributes.addFlashAttribute("successMessage", "Order status updated successfully!");
        } catch (Exception e) {
            // Handle exceptions and display error message
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

	    return "redirect:/admin/view/all/orders"; 
    }


 // Delete Order by Admin
    @PostMapping("/admin/order/{orderId}/delete")
    public String deleteOrder(@PathVariable("orderId") Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderService.deleteOrder(orderId); // Call service to delete the order
            redirectAttributes.addFlashAttribute("successMessage", "Order deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete order: " + e.getMessage());
        }
        return "redirect:/admin/view/all/orders"; // Redirect to the orders list
    }

	
	//for menu item 
	
	    // Get all MenuItems by admin only
	    @GetMapping("/admin/getAllMenuItem")
	    public String getAllMenuItems(Model model) {
	        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
	        model.addAttribute("menuItems",menuItems);
	        return "menu-list";
	    }


	    //only admin can perform these roles 
	    //add new menu Items 
	    @PostMapping("/admin/addMenuItem")
		    public String createMenuItem(@ModelAttribute("menuItems") MenuItem menuItem) {
		        MenuItem createdMenuItem = menuItemService.addMenuItem(menuItem);
		    	System.out.println(menuItem.getItemName());
		    	System.out.println(menuItem.getItemPrice());
		        
		        return "redirect:/admin/getAllMenuItem";
		 }
		 
	    
	    //Update Menu Item
	    @PostMapping("/admin/updateMenuItem/{id}")
	    public String updateMenuItem(@ModelAttribute("menuItem") MenuItem menuItem,@PathVariable("id") Integer id ) {
	    	MenuItem postedMenuItem = menuItemService.updateMenuItem(id,menuItem);
	        
	    	System.out.println(postedMenuItem);
	    	System.out.println(postedMenuItem.getItemName());
	    	System.out.println(postedMenuItem.getItemPrice());
	    	return "redirect:/admin/getAllMenuItem";
	    }

	    
	    // Delete Menu Item
	    @PostMapping("/admin/deleteMenuItem")
	    public String deleteMenuItem(@RequestParam("itemId") Integer itemId) {
	        menuItemService.deleteMenuItem(itemId);
	        return "redirect:/admin/getAllMenuItem";
	    }
	    
	    
	    
	    
}
