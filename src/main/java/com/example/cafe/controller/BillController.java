package com.example.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.cafe.entity.Bills;
import com.example.cafe.service.BillService;

@Controller
public class BillController {

    @Autowired
    private BillService billService;

    // Show all bills
    @GetMapping("/bills")
    public String viewAllBills(Model model) {
        model.addAttribute("bills", billService.getAllBills());
        return "bills";  
    }

    // Show individual bill details
    @GetMapping("/bills/{billId}")
    public String viewBillDetails(@PathVariable Integer billId, Model model) {
        Bills bill = billService.getBillById(billId);
        model.addAttribute("bill", bill);
        return "bill-details"; 
    }

    // Mark a bill as paid
    @PostMapping("/bills/{billId}/pay")
    public String markAsPaid(@PathVariable Integer billId) {
        billService.markAsPaid(billId);
        return "redirect:/bills";  
    }
}
