package com.example.cafe.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Bills {
	


@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private  Integer billId;

private Integer totalPrice;

@CreationTimestamp 
private LocalDate billDate;


@ManyToOne
@JoinColumn(name="Order_id",referencedColumnName="orderId")
private Orders order;



public Orders getOrder() {
	return order;
}

public void setOrder(Orders order) {
	this.order = order;
}


public Integer getBillId() {
	return billId;
}

public void setBillId(Integer billId) {
	this.billId = billId;
}

public Integer getTotalPrice() {
	return totalPrice;
}

public void setTotalPrice(Integer totalPrice) {
	this.totalPrice = totalPrice;
}

public LocalDate getBillDate() {
	return billDate;
}

public void setBillDate(LocalDate billDate) {
	this.billDate = billDate;
}

public Orders getOrderId() {
	return order;
}

public void setOrderId(Orders orderId) {
	order = orderId;
}


}
