package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductService service;
	
	@PostMapping("/place")
	public ResponseEntity<?> placOrder(@RequestParam Long userId, @RequestParam Integer quantity,@RequestParam Long productId){
		return ResponseEntity.ok(service.placeOrder(quantity,userId,productId));
	}
	
	@GetMapping("/{orderID}")
	public ResponseEntity<?> getOrdersByID(){
		return null;
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllOrders(){
		return ResponseEntity.ok(orderRepository.findAll());
	}
}
