package com.example.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cafe.entity.Bills;
import com.example.cafe.enums.PaymentStatus;

public interface BillRepository extends JpaRepository<Bills, Integer> {

    
    long countByPaymentStatus(PaymentStatus status);
}
