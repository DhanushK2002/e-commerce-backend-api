package com.ecommerce.dto;

import com.ecommerce.model.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderResponse {
	private Long orderId;
    private Integer quantity;
    private String username;
    private String productName;
    private String address;

	public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.quantity = order.getQuantity();
        if(order.getUser() != null) {
        	this.username = order.getUser().getUsername();
        	this.address = order.getUser().getAddress();
        }else {
        	this.username = "Unknown user(old record)";
        	this.address = "No address";
        }
        if(order.getProduct() != null) {
        	this.productName = order.getProduct().getProductName();
        } 
    }
}
