package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.OrderResponseRequest;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private ProductService service;
	
	@PostMapping("/place")
	public ResponseEntity<?> placOrder(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity){
		return ResponseEntity.ok(service.placeOrder(userId, productId, quantity));
	}
	
	@GetMapping("/myorders")
	public List<OrderResponseRequest> getMyOrders(){
		return service.getMyOrders();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<?> getAllOrders(){
		return service.getAllOrders();
	}
}
