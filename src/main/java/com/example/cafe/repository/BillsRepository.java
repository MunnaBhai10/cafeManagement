package com.example.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cafe.entity.Bills;

@Repository
public interface BillsRepository extends JpaRepository<Bills, Integer> {

    
   // long countByPaymentStatus(PaymentStatus status);
}
