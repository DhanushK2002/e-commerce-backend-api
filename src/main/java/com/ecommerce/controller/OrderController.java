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
import com.ecommerce.dto.OrderRespone;
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
	public ResponseEntity<?> placeOrder(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity){
		String order = productService.placeOrder(userId, productId, quantity);
		ApiResponse<?> response = new ApiResponse<>(true, "Order placed successfully",order,LocalDateTime.now());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/myorders")
	public ResponseEntity<ApiResponse<OrderRespone>> getMyOrders(){
		List<OrderRespone> orders = productService.getMyOrders();
		ApiResponse<OrderRespone> response = new ApiResponse(true, "Your Orders", orders, LocalDateTime.now());
		return ResponseEntity.ok(response);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<ApiResponse<?>> getAllOrders(){
		ResponseEntity<?> allOrders = productService.getAllOrders();
		ApiResponse<?> response = new ApiResponse(true, "All orders", allOrders, LocalDateTime.now());
		return ResponseEntity.ok(response);
	}
}
