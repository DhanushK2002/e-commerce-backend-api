package com.ecommerce.controller;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.dto.PageResponse;
import com.ecommerce.dto.SubCategoryDto;
import com.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;

	@GetMapping("/category")
	public ApiResponse<PageResponse<CategoryResponse>> getAllCategories(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return categoryService.getAllCategories(pageable);
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
