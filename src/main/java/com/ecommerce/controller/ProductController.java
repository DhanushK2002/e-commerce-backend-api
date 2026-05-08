package com.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.dto.SubCategoryDto;
import com.ecommerce.model.Product;
import com.ecommerce.serviceImpl.CategoryServiceImpl;
import com.ecommerce.serviceImpl.ProductServiceImplementation;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductServiceImplementation productService;
	private final CategoryServiceImpl categoryService;

	public ProductController(ProductServiceImplementation productService, CategoryServiceImpl categoryService) {
		super();
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@GetMapping("/category")
	public ApiResponse<List<CategoryResponse>> getAllCategories(@RequestParam int page,
			@RequestParam int size, @RequestParam String sortDir, @RequestParam String sortBy) {
		return categoryService.getAllCategories(page, size, sortDir, sortBy);
	}

	@GetMapping("/category/{categoryName}")
	public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryByName(@PathVariable String categoryName) {
		CategoryResponse category = categoryService.findByCategoryName(categoryName);

		boolean success = false;
		String message = "Sorry! no such category found";
		if (category != null) {
			success = true;
			message = "Respected category fetched successfully";
		}
		ApiResponse<CategoryResponse> response = new ApiResponse<CategoryResponse>(success, message, category,
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

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

	@GetMapping("/subcategory/{subCategoryName}")
	public ResponseEntity<ApiResponse<SubCategoryDto>> getSubCategoryByName(@PathVariable String subCategoryName) {
		SubCategoryDto subCategory = categoryService.getSubCategoryByName(subCategoryName);

		boolean success = false;
		String message = "Sorry! respected Sub-Category not found";
		if (subCategory != null) {
			success = true;
			message = "Respected Subcategory fetched successfully";
		}
		ApiResponse<SubCategoryDto> response = new ApiResponse<SubCategoryDto>(success, message, subCategory,
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	// ADMIN

	// Add New Product
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/admin/add")
	public ApiResponse<Void> addProduct(@RequestBody Product product, Authentication auth) {
		return productService.addProduct(product, auth.getName());
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
