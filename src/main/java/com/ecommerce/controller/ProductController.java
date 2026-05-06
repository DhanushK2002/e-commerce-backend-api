package com.ecommerce.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CategoryDto;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.SubCategoryDto;
import com.ecommerce.serviceImpl.CategoryServiceImpl;
import com.ecommerce.serviceImpl.ProductServiceImplementation;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductServiceImplementation productService;

	@Autowired
	private CategoryServiceImpl categoryService;

	@GetMapping("/category")
	public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories(@RequestParam int page,
			@RequestParam int size, @RequestParam String sortDir, @RequestParam String sortBy) {

		List<CategoryDto> categories = categoryService.getAllCategories(page, size, sortDir, sortBy);

		boolean success = false;
		String message = "Sorry! no such category found";

		if (categories != null) {
			success = true;
			message = "Product Category Fetched successfully";
		}

		ApiResponse<List<CategoryDto>> response = new ApiResponse<List<CategoryDto>>(success, message, categories,
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/category/{categoryName}")
	public ResponseEntity<ApiResponse<CategoryDto>> getCategoryByName(@PathVariable String categoryName) {
		CategoryDto category = categoryService.findByCategoryName(categoryName);

		boolean success = false;
		String message = "Sorry! no such category found";
		if (category != null) {
			success = true;
			message = "Respected category fetched successfully";
		}
		ApiResponse<CategoryDto> response = new ApiResponse<CategoryDto>(success, message, category,
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	// Find Product By ID
	@GetMapping("/{productId}")
	public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable Long productId) {
		ProductDto product = productService.getProductById(productId);

		boolean success = false;
		String message = "Sorry! no product found";
		if (product != null) {
			success = true;
			message = "Product Fetched successfully";
		}

		ApiResponse<ProductDto> response = new ApiResponse<ProductDto>(success, message, product, LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	// List of All Products
	@GetMapping
	public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts() {
		List<ProductDto> products = productService.getAllProducts();

		boolean success = false;
		String message = "Sorry! can't fetch products";
		if (products != null) {
			success = true;
			message = "Products fetched Successfully";
		}
		ApiResponse<List<ProductDto>> response = new ApiResponse<List<ProductDto>>(success, message, products,
				LocalDateTime.now());

		return ResponseEntity.ok(response);
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
	public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto, Authentication auth) {
		return ResponseEntity.ok(productService.addProduct(productDto, auth.getName()));
	}

	// Update Product By ID
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/admin/update/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
		return ResponseEntity.ok(productService.updateProduct(productId, productDto));
	}

	// Delete Product By ID
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/admin/delete/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return ResponseEntity.ok("Product Deleted Successfully");
	}
}
