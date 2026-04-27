package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ProductRequest;
import com.ecommerce.model.Product;
import com.ecommerce.serviceImpl.ProductServiceImplementation;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductServiceImplementation service;
	
	// List of All Products
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok(service.getAllProducts());
	}
	
	//Add New Product
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<ProductRequest> addProduct(@RequestBody ProductRequest productDto){
		return ResponseEntity.ok(service.addProduct(productDto));
	}
	
	//Find Product By ID
	@GetMapping("/{productId}")
	public ResponseEntity<ProductRequest> getProductById(@PathVariable Long productId){
		return ResponseEntity.ok(service.getProductById(productId));
	}
	
	//Update Product By ID
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{productId}")
	public ResponseEntity<ProductRequest> updateProduct(@PathVariable Long productId,@RequestBody ProductRequest productDto) {
		return ResponseEntity.ok(service.updateProduct(productId, productDto));
	}
	
	//Find Products By Category 
	@GetMapping("/search/{productCategory}")
	public ResponseEntity<List<ProductRequest>> getProductByCategory(@PathVariable String productCategory){
		return ResponseEntity.ok(service.getProductByCategory(productCategory));
	}
	
	//Delete Product By ID
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
		service.deleteProduct(productId);
		return ResponseEntity.ok("Product Deleted Successfully");
	}
	
//	//Place Order
//	@PostMapping("/place")
//	public String placeOrder(@RequestParam Long productId, @RequestParam Integer quantity) {
//		return service.placeOrder(quantity,productId);
//	}
}
