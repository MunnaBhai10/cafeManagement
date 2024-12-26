package com.example.cafe.DTO;

public class OrderItemDTO {
    private Integer menuItemId;
    private Integer quantity;
    private String itemName;  // Add name field
    private Double itemPrice; // Add price field
    
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
	public Integer getMenuItemId() {
		return menuItemId;
	}
	public void setMenuItemId(Integer menuItemId) {
		this.menuItemId = menuItemId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

    // Getters and Setters
    
    
}
