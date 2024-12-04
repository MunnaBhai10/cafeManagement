package com.example.cafe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cafe.enums.PaymentStatus;
import com.example.cafe.enums.TableStatus;
import com.example.cafe.repository.BillRepository;
import com.example.cafe.repository.CafeTableRepository;
import com.example.cafe.repository.OrderRepository;
import com.example.cafe.repository.UserRepository;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CafeTableRepository cafeTablesRepository;
    
    @Autowired
    private BillRepository billRepository;

    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getTotalOrders() {
        return orderRepository.count();
    }

    public long getActiveTables() {
        return cafeTablesRepository.countByStatus(TableStatus.AVAILABLE);
    }

    public long getPendingBills() {
        return billRepository.countByPaymentStatus(PaymentStatus.PENDING);
    }
}
