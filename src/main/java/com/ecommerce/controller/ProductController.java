package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductServiceImplementation productService;
	
	// List of All Products
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	//Add New Product
//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<ProductRequest> addProduct(@Valid @RequestBody ProductRequest productDto, Authentication auth){
		return ResponseEntity.ok(productService.addProduct(productDto,auth.getName()));
	}
	
	//Find Product By ID
	@GetMapping("/{productId}")
	public ResponseEntity<ProductRequest> getProductById(@PathVariable Long productId){
		return ResponseEntity.ok(productService.getProductById(productId));
	}
	
	//Update Product By ID
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{productId}")
	public ResponseEntity<ProductRequest> updateProduct(@PathVariable Long productId,@RequestBody ProductRequest productDto) {
		return ResponseEntity.ok(productService.updateProduct(productId, productDto));
	}
	
	//Find Products By Category 
	@GetMapping("/search/{productCategory}")
	public ResponseEntity<List<ProductRequest>> getProductByCategory(@PathVariable String productCategory){
		return ResponseEntity.ok(productService.getProductByCategory(productCategory));
	}
	
	//Delete Product By ID
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return ResponseEntity.ok("Product Deleted Successfully");
	}
}
