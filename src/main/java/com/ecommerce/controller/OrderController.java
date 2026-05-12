package com.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.service.ProductService;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	private final ProductService productService;
	
	public OrderController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@PostMapping("/place")
	public ApiResponse<?> placeOrder(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity){
		String order = productService.placeOrder(userId, productId, quantity); 
		return new ApiResponse<>(true, "Order placed successfully",order,LocalDateTime.now());
	}
	
	@GetMapping("/myorders")
	public ApiResponse<List<OrderResponse>> getMyOrders(){
		List<OrderResponse> orders = productService.getMyOrders();
		return new ApiResponse<List<OrderResponse>>(true, "Your Orders", orders, LocalDateTime.now());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/all")
	public ApiResponse<List<OrderResponse>> getAllOrders(){
		return productService.getAllOrders();
	}
}
