package com.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.dto.OrderRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.service.ProductService;


@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
	
	private final ProductService productService;

	@PostMapping("/place")
	public ApiResponse<?> placeOrder(@Valid @RequestBody OrderRequest orderRequest){
		String order = productService.placeOrder(orderRequest);
		return new ApiResponse<>(true, "Order placed successfully",order,LocalDateTime.now(), ResponseEntity.status(HttpStatus.OK));
	}
	
	@GetMapping("/myorders")
	public ApiResponse<List<OrderResponse>> getMyOrders(){
		return productService.getMyOrders();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/all")
	public ApiResponse<List<OrderResponse>> getAllOrders(){
		return productService.getAllOrders();
	}
}
