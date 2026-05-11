package com.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.dto.SubCategoryDto;
import com.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	@GetMapping("/category")
	public ApiResponse<List<CategoryResponse>> getAllCategories(@RequestParam int page,
			@RequestParam int size, @RequestParam String sortDir, @RequestParam String sortBy) {
		return categoryService.getAllCategories(page, size, sortDir, sortBy);
	}

	@GetMapping("/category/{categoryName}")
	public ApiResponse<CategoryResponse> findCategoryByName(@PathVariable String categoryName) {
		return categoryService.findByCategoryName(categoryName);
	}

	@GetMapping("/subcategory/{subCategoryName}")
	public ApiResponse<List<SubCategoryDto>> getSubCategoryByName(@PathVariable String subCategoryName) {	
		return categoryService.getSubCategoryByName(subCategoryName);
	}
}
