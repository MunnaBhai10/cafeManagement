package com.example.cafe.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.cafe.enums.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Orders {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer orderId;

@CreationTimestamp
private LocalDateTime orderTime;

@Enumerated
private OrderStatus status;

@ManyToOne
@JoinColumn(name= "table_id", referencedColumnName = "tableId")
private CafeTables table;

@ManyToOne
@JoinColumn(name= "customer_id", referencedColumnName = "userId")
private Users user;

public Integer getOrderId() {
	return orderId;
}

public void setOrderId(Integer orderId) {
	this.orderId = orderId;
}

public LocalDateTime getOrderTime() {
	return orderTime;
}

public void setOrderTime(LocalDateTime orderTime) {
	this.orderTime = orderTime;
}

public OrderStatus getStatus() {
	return status;
}

public void setStatus(OrderStatus status) {
	this.status = status;
}

public CafeTables getTable() {
	return table;
}

public void setTable(CafeTables table) {
	this.table = table;
}

public Users getUser() {
	return user;
}

public void setUser(Users user) {
	this.user = user;
}

}
