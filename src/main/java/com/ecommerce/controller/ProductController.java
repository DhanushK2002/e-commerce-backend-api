package com.ecommerce.controller;

import java.util.List;

import lombok.AllArgsConstructor;
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
	public ApiResponse<ProductResponse> getProductById(@PathVariable Long productId) {
		return productService.getProductById(productId);
	}

	// List of All Products
	@GetMapping
	public ApiResponse<List<ProductResponse>> getAllProducts() {
		return  productService.getAllProducts();
	}

	// ADMIN

	// Add New Product
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/admin/add")
	public ApiResponse<Void> addProduct(@RequestBody ProductRequest productRequest, Authentication auth) {
		return productService.addProduct(productRequest, auth.getName());
	}

	// Update Product By ID
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/admin/update/{productId}")
	public ApiResponse<ProductResponse> updateProduct(@PathVariable Long productId,
			@RequestBody ProductRequest request) {
		return productService.updateProduct(productId, request);
	}

	// Delete Product By ID
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/admin/delete/{productId}")
	public ApiResponse<Void> deleteProduct(@PathVariable Long productId) {
		return productService.deleteProduct(productId);
	}
}
