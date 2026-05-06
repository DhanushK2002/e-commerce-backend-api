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
	public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
		List<CategoryDto> categories = categoryService.getAllCategories();

		ApiResponse<List<CategoryDto>> response = new ApiResponse<List<CategoryDto>>(true, "Product Category Fetched successfully", categories,
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/category/{categoryName}")
	public ResponseEntity<ApiResponse<CategoryDto>> getCategoryByName(@PathVariable String categoryName) {
		CategoryDto category = categoryService.findByCategoryName(categoryName);

		ApiResponse<CategoryDto> response = new ApiResponse<CategoryDto>(true, "Respected category fetched successfully", category,
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	// Find Product By ID
	@GetMapping("/{productId}")
	public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable Long productId) {
		ProductDto product = productService.getProductById(productId);

		ApiResponse<ProductDto> response = new ApiResponse<ProductDto>(true, "Product Fetched successfully", product,
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	// List of All Products
	@GetMapping
	public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts() {
		List<ProductDto> products = productService.getAllProducts();

		ApiResponse<List<ProductDto>> response = new ApiResponse<List<ProductDto>>(true, "Successfully fetched", products, LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/subcategory/{subCategoryName}")
	public ResponseEntity<ApiResponse<SubCategoryDto>> getSubCategoryByName(@PathVariable String subCategoryName) {
		SubCategoryDto subCategory = categoryService.getSubCategoryByName(subCategoryName);

		ApiResponse<SubCategoryDto> response = new ApiResponse<SubCategoryDto>(true, "Respected Subcategory fetched successfully",
				subCategory, LocalDateTime.now());

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
