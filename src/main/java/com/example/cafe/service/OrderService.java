package com.example.cafe.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cafe.DTO.OrderItemDTO;
import com.example.cafe.entity.MenuItem;
import com.example.cafe.entity.OrderItem;
import com.example.cafe.entity.Orders;
import com.example.cafe.entity.Users;
import com.example.cafe.repository.MenuItemRepository;
import com.example.cafe.repository.OrderRepository;
import com.example.cafe.repository.UsersRepository;
@Service
public class OrderService {

    @Autowired
    private MenuItemRepository menuItemRepository;
    
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    
    
    public Orders createOrder(List<OrderItemDTO> orderItemDTOs, Integer userId) throws Exception {
    	
    	System.out.println("service to create order");
        Orders order = new Orders();
       
        List<OrderItem> orderItemsList = new ArrayList<>();
        double totalAmount = 0;

        // Loop through the list of OrderItemDTOs
        for (OrderItemDTO dto : orderItemDTOs) {
            // Get the corresponding MenuItem based on the DTO
            MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + dto.getMenuItemId()));

            // Create a new OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem); 
            orderItem.setQuantity(dto.getQuantity()); 
            //calculating total by using menuitem price times quantity
            orderItem.setTotalPrice(menuItem.getItemPrice() * dto.getQuantity());

            // Set the relationship back to the Order
            orderItem.setOrder(order); 

            // Add OrderItem to the list of orderItems
            orderItemsList.add(orderItem);

            // Accumulate the total amount for the order
            totalAmount += orderItem.getTotalPrice();
        }

        // Find the user by ID
        Optional<Users> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new Exception("User cannot be found by id");
        }

        //saving the order of user
        order.setUser(user.get());
        order.setOrderItems(orderItemsList);//set orderItems
        order.setTotalAmount(totalAmount);  // Set the total amount
        order.setOrderDate(LocalDateTime.now());  // Set the order date to the current time

        // Save the order and order items (cascade will save order items if configured)
        order = orderRepository.save(order);  // Save the order (this will cascade to save order items if configured)

        return order;  // Return the saved order
    }

    
    
    
}
