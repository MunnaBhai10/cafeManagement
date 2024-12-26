package com.example.cafe.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cafe.DTO.OrderItemDTO;
import com.example.cafe.entity.MenuItem;
import com.example.cafe.entity.OrderItem;
import com.example.cafe.entity.Orders;
import com.example.cafe.entity.Users;
import com.example.cafe.repository.MenuItemRepository;
import com.example.cafe.repository.OrderItemRepository;
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

    @Autowired
    private OrderItemRepository orderItemRepository;

    
    
    public Orders createOrder(List<OrderItemDTO> orderItemDTOs, Long userId) throws Exception {
    	
    	System.out.println("service to create order");
        Orders order = new Orders();
       
        List<OrderItem> orderItemsList = new ArrayList<>();
        double totalAmount = 0;

        // Find the user by ID
        Optional<Users> user = userRepository.findById(userId);
        if (user.isEmpty()) {
        	throw new Exception("User cannot be found by id");
        }
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
        
        //saving the order of user
        order.setUser(user.get());
        order.setOrderItems(orderItemsList);//set orderItems
        order.setTotalAmount(totalAmount);  // Set the total amount
        order.setOrderDate(LocalDateTime.now());  // Set the order date to the current time
        order.setOrderStatus("pending");
        order.setPaymentStatus("not-paid");

        // Save the order and order items (cascade will save order items if configured)
        order = orderRepository.save(order);  // Save the order (this will cascade to save order items if configured)

        return order;  // Return the saved order
    }

    public void updateOrderStatus(Long orderId, String status) throws Exception {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));
        if ("paid".equals(order.getPaymentStatus())) {
            order.setOrderStatus(status);
            orderRepository.save(order);
        } else {
            throw new Exception("Order can only be marked as 'order-sent' if payment status is 'paid'.");
        }
    }

    
    
    
    public List<Orders> getAllOrders() {
        return orderRepository.findAll(); // Fetch all orders from the database
    }
    
   
     //Retrieve an order by its ID.
     
    public Orders getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }


     // Retrieve all order items associated with an order.

    public List<OrderItemDTO> getOrderItemsByOrderId(Long orderId) throws Exception {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId); // Assuming you have this method
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        for (OrderItem item : orderItems) {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setMenuItemId(item.getMenuItem().getItemId());
            dto.setItemName(item.getMenuItem().getItemName());
            dto.setItemPrice(item.getMenuItem().getItemPrice());
            dto.setQuantity(item.getQuantity());
            orderItemDTOs.add(dto);
        }
        return orderItemDTOs;
    }
    
    
    // Save or update an order
    public void saveOrder(Orders order) {
        orderRepository.save(order);
    }


    // Cancel an order by updating its status to "Canceled".
    public void cancelOrder(Long orderId) throws Exception {
        Orders order = getOrderById(orderId);
        order.setOrderStatus("Canceled"); // Update the status to canceled
        order.setPaymentStatus("Canceled"); // Update the status to canceled
        orderRepository.save(order); // Save the changes
    }


     //Mark an order as paid by updating its status to "Paid".
    public void payOrder(Long orderId) throws Exception {
        Orders order = getOrderById(orderId);
        order.setPaymentStatus("paid"); // Update the status to paid
        order.setOrderStatus("not-recieved");
        orderRepository.save(order); // Save the changes
    }




    //to mark as recieved Order by customer
    public void markAsReceived(Long orderId) throws Exception {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));
        
        if ("order-sent".equals(order.getOrderStatus())) {
            order.setOrderStatus("Completed"); // Update the status
            orderRepository.save(order); // Save the updated order
        } else {
            throw new Exception("Invalid order status for marking as received");
        }
    }

 // Delete an order by ID
    public void deleteOrder(Long orderId) throws Exception {
        if (!orderRepository.existsById(orderId)) {
            throw new Exception("Order not found");
        }
        orderRepository.deleteById(orderId);
    }

    public List<Orders> getOrdersInLast24Hours(LocalDateTime twentyFourHoursAgo) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getOrderDate() != null && order.getOrderDate().isAfter(twentyFourHoursAgo))
                .collect(Collectors.toList());
    }


}
