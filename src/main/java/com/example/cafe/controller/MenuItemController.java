package com.example.cafe.controller;

import com.example.cafe.entity.MenuItem;
import com.example.cafe.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menu")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping("/list")
    public String getAllMenuItems(Model model) {
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        return "menu-list";
    }

    @GetMapping("/new")
    public String createMenuItemForm(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        return "menu-form";
    }

    @PostMapping("/save")
    public String saveMenuItem(@ModelAttribute("menuItem") MenuItem menuItem) {
        menuItemService.createMenuItem(menuItem);
        return "redirect:/menu/list";
    }

    @GetMapping("/edit/{id}")
    public String editMenuItemForm(@PathVariable("id") Integer menuItemId, Model model) {
        model.addAttribute("menuItem", menuItemService.getMenuItemById(menuItemId));
        return "menu-form";
    }
}
