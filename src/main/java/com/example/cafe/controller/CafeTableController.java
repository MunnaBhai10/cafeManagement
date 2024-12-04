package com.example.cafe.controller;

import com.example.cafe.entity.CafeTables;
import com.example.cafe.service.CafeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tables")
public class CafeTableController {

    @Autowired
    private CafeTableService cafeTableService;

    @GetMapping("/list")
    public String getAllCafeTables(Model model) {
        model.addAttribute("tables", cafeTableService.getAllCafeTables());
        return "table-list";
    }

    @GetMapping("/new")
    public String createCafeTableForm(Model model) {
        model.addAttribute("table", new CafeTables());
        return "table-form";
    }

    @PostMapping("/save")
    public String saveCafeTable(@ModelAttribute("table") CafeTables cafeTable) {
        cafeTableService.createCafeTable(cafeTable);
        return "redirect:/tables/list";
    }

    @GetMapping("/edit/{id}")
    public String editCafeTableForm(@PathVariable("id") Integer tableId, Model model) {
        model.addAttribute("table", cafeTableService.getCafeTableById(tableId));
        return "table-form";
    }
}
