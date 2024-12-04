package com.example.cafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cafe.entity.Orderdetails;

public interface OrderdetailsRepository extends JpaRepository<Orderdetails, Integer> {

    // Custom query to find order details by order ID
    List<Orderdetails> findByOrderOrderId(Integer orderId);
}
