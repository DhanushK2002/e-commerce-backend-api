package com.ecommerce.controller;

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
	public ResponseEntity<ApiResponse<String>> placeOrder(@Valid @RequestBody OrderRequest orderRequest){
		ApiResponse<String> response = productService.placeOrder(orderRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/myorders")
	public ResponseEntity<ApiResponse<List<OrderResponse>>> getMyOrders(){
		ApiResponse<List<OrderResponse>> response = productService.getMyOrders();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders(){
		ApiResponse<List<OrderResponse>> response = productService.getAllOrders();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
