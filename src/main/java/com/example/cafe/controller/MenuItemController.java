package com.example.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.cafe.entity.MenuItem;
import com.example.cafe.service.MenuItemService;


@Controller
public class MenuItemController {

	@Autowired	
	private MenuItemService menuItemService;

	    // Get all MenuItems
	    @GetMapping("/getAllMenuItem")
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
		        
		        return "redirect:/getAllMenuItem";
		 }
		 
	    
	    //Update Menu Item
	    @PostMapping("/admin/updateMenuItem/{id}")
	    public String updateMenuItem(@ModelAttribute("menuItem") MenuItem menuItem,@PathVariable("id") Integer id ) {
	    	MenuItem postedMenuItem = menuItemService.updateMenuItem(id,menuItem);
	        
	    	System.out.println(postedMenuItem);
	    	System.out.println(postedMenuItem.getItemName());
	    	System.out.println(postedMenuItem.getItemPrice());
	    	return "redirect:/getAllMenuItem";
	    }

	    
	    // Delete Menu Item
	    @PostMapping("/admin/deleteMenuItem")
	    public String deleteMenuItem(@RequestParam("itemId") Integer itemId) {
	        menuItemService.deleteMenuItem(itemId);
	        return "redirect:/getAllMenuItem";
	    }
	    
}
