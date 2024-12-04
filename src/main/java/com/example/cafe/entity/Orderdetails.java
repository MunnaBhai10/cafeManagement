package com.example.cafe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Orderdetails {
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
private Integer orderDetailsId;

@ManyToOne
@JoinColumn(name="order_id",referencedColumnName="orderId")
private Orders order;

@ManyToOne
@JoinColumn(name="item_id",referencedColumnName="itemId")
private MenuItem itemId;

private Integer quantity;

private Long totalPrice;


public Integer getOrderDetailsId() {
	return orderDetailsId;
}

public void setOrderDetailsId(Integer orderDetailsId) {
	this.orderDetailsId = orderDetailsId;
}

public Orders getOrder() {
	return order;
}

public void setOrder(Orders order) {
	this.order = order;
}

public MenuItem getItemId() {
	return itemId;
}

public void setItemId(MenuItem itemId) {
	this.itemId = itemId;
}

public Integer getQuantity() {
	return quantity;
}

public void setQuantity(Integer quantity) {
	this.quantity = quantity;
}

public Long getTotalPrice() {
	return totalPrice;
}

public void setTotalPrice(Long totalPrice) {
	this.totalPrice = totalPrice;
}

}
