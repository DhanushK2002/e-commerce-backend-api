package com.ecommerce.dto;

import com.ecommerce.model.Order;

public class OrderRespone {
	private Long orderId;
    private Integer quantity;
    private String username;
    private String productName;
    private String address;

    public OrderRespone(Order order) {
        this.orderId = order.getOrderId();
        this.quantity = order.getQuantity();
        if(order.getUser() != null) {
        	this.username = order.getUser().getUsername();
        	this.address = order.getUser().getAddress();
        }
        if(order.getProduct() != null) {
        	this.productName = order.getProduct().getProductName();
        } 
    }

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
    
    
}
