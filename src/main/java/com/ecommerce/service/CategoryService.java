package com.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.dto.PageResponse;
import com.ecommerce.dto.SubCategoryDto;

public interface CategoryService {
	public ApiResponse<PageResponse<CategoryResponse>> getAllCategories(Pageable pageable);
	public ApiResponse<List<SubCategoryDto>> getSubCategoryByName(String subCategoryName);
	public ApiResponse<CategoryResponse> findByCategoryName(String categoryName);
}
