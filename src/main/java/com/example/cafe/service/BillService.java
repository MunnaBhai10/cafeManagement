package com.example.cafe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cafe.entity.Bills;
import com.example.cafe.enums.PaymentStatus;
import com.example.cafe.repository.BillRepository;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    // Get all bills
    public List<Bills> getAllBills() {
        return billRepository.findAll();
    }

    // Get a bill by its ID
    public Bills getBillById(Integer billId) {
        Optional<Bills> bill = billRepository.findById(billId);
        return bill.orElse(null);  // Return null if bill not found
    }

    // Mark a bill as paid
    public void markAsPaid(Integer billId) {
        Optional<Bills> bill = billRepository.findById(billId);
        if (bill.isPresent()) {
            Bills existingBill = bill.get();
            existingBill.setPaymentStatus(PaymentStatus.PAID);  // Assuming 'PAID' is a valid PaymentStatus
            billRepository.save(existingBill);  // Save the updated bill
        }
    }
}
