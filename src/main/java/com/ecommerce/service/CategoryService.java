package com.ecommerce.service;

import java.util.List;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.dto.SubCategoryDto;

public interface CategoryService {
	public ApiResponse<List<CategoryResponse>> getAllCategories(int page, int size, String sortDir, String sortBy);
	public SubCategoryDto getSubCategoryByName(String subCategoryName);
	public CategoryResponse findByCategoryName(String categoryName);
}
