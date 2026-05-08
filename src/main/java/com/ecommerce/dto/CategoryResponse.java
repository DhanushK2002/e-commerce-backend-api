package com.ecommerce.dto;

import java.util.List;

public class CategoryResponse {
	private Long categoryId;
	private String categoryName;
	private List<SubCategoryDto> subCategories;

	public CategoryResponse() {
		super();
	}

	public CategoryResponse(Long categoryId, String categoryName, List<SubCategoryDto> subCategories) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.subCategories = subCategories;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<SubCategoryDto> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<SubCategoryDto> subCategories) {
		this.subCategories = subCategories;
	}
}
