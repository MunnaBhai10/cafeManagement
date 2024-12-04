package com.example.cafe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cafe.entity.Orderdetails;
import com.example.cafe.repository.OrderdetailsRepository;

@Service
public class OrderdetailsService {

    @Autowired
    private OrderdetailsRepository orderdetailsRepository;

    // Get all order details by order ID
    public List<Orderdetails> getOrderDetailsByOrderId(Integer orderId) {
        return orderdetailsRepository.findByOrderOrderId(orderId);
    }

    // Save order details
    public Orderdetails saveOrderdetails(Orderdetails orderdetails) {
        return orderdetailsRepository.save(orderdetails);
    }

    // Get all order details
    public List<Orderdetails> getAllOrderDetails() {
        return orderdetailsRepository.findAll();
    }
}
