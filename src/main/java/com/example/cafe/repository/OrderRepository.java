package com.example.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cafe.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {


	
}
