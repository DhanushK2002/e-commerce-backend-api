package com.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.serviceImpl.CategoryServiceImpl;
import com.ecommerce.serviceImpl.ProductServiceImplementation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductServiceImplementation productService;

	@Autowired
	private CategoryServiceImpl categoryService;

	@GetMapping("/category")
	public ResponseEntity<?> getCategories() {
		List<Category> categories = categoryService.getAllCategories();

		ApiResponse<Category> response = new ApiResponse(true, "Product Category Fetched successfully", categories,
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/category/{categoryName}")
	public ResponseEntity<?> getCategoryByName(@PathVariable String categoryName) {
		Optional<Category> category = categoryService.findByCategoryName(categoryName);

		ApiResponse<Category> response = new ApiResponse(true, "Respected category fetched successfully", category,
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	// Find Product By ID
	@GetMapping("/{productId}")
	public ResponseEntity<ApiResponse<ProductRequest>> getProductById(@PathVariable Long productId) {
		ProductRequest product = productService.getProductById(productId);

		ApiResponse<ProductRequest> response = new ApiResponse(true, "Product Fetched successfully", product,
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	// List of All Products
	@GetMapping
	public ResponseEntity<ApiResponse<Product>> getAllProducts() {
		List<Product> product = productService.getAllProducts();

		System.out.println(product);
		ApiResponse<Product> response = new ApiResponse(true, "Successfully fetched", product, LocalDateTime.now());
		System.out.println(product);

		return ResponseEntity.ok(response);
	}

	// Find Products By Category
	@GetMapping("/search/{productCategory}")
	public ResponseEntity<ApiResponse<ProductRequest>> getProductByCategory(@PathVariable String productCategory) {
		List<ProductRequest> product = productService.getProductByCategory(productCategory);

		ApiResponse<ProductRequest> response = new ApiResponse(true, "Product Category Fetched successfully", product,
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	// Add New Product
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<ProductRequest> addProduct(@Valid @RequestBody ProductRequest productDto,
			Authentication auth) {
		return ResponseEntity.ok(productService.addProduct(productDto, auth.getName()));
	}

	// Update Product By ID
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{productId}")
	public ResponseEntity<ProductRequest> updateProduct(@PathVariable Long productId,
			@RequestBody ProductRequest productDto) {
		return ResponseEntity.ok(productService.updateProduct(productId, productDto));
	}

	// Delete Product By ID
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return ResponseEntity.ok("Product Deleted Successfully");
	}
}
