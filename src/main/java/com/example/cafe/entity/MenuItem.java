package com.example.cafe.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class MenuItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer itemId;
	
	private String itemName;
	private Double itemPrice;
	private String menuImage;
	
    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
	
	public Integer getItemId() {
		return itemId;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getMenuImage() {
		return menuImage;
	}
	public void setMenuImage(String menuImage) {
		this.menuImage = menuImage;
	}


}
