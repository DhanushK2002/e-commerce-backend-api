package com.ecommerce.controller;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.service.ProductService;
import com.ecommerce.serviceImpl.ProductServiceImplementation;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	// Find Product By ID
	@GetMapping("/{productId}")
	public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long productId) {
		ApiResponse<ProductResponse> response = productService.getProductById(productId);
		return ResponseEntity.status(HttpStatus.FOUND).body(response);
	}

	// List of All Products
	@GetMapping
	public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
		ApiResponse<List<ProductResponse>> response = productService.getAllProducts();
		return  ResponseEntity.status(HttpStatus.FOUND).body(response);
	}

	// ADMIN

	// Add New Product
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/admin/add")
	public ResponseEntity<ApiResponse<Void>> addProduct(@RequestBody ProductRequest productRequest, Authentication auth) {
		ApiResponse<Void> response = productService.addProduct(productRequest, auth.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// Update Product By ID
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/admin/update/{productId}")
	public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Long productId,
			@RequestBody ProductRequest request) {
		ApiResponse<ProductResponse> response = productService.updateProduct(productId, request);
		return ResponseEntity.status(HttpStatus.FOUND).body(response);
	}

	// Delete Product By ID
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/admin/delete/{productId}")
	public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
		ApiResponse<Void> response = productService.deleteProduct(productId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
