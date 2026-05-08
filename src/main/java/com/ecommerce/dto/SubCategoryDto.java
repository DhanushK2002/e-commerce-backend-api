package com.ecommerce.dto;

import java.util.List;

import com.ecommerce.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SubCategoryDto {
	private Long subCategoryId;
	private String subCategoryName;
	@JsonIgnore
	private Category category;
	private List<ProductResponse> products;

	public SubCategoryDto() {
		super();
	}

	public SubCategoryDto(Long subCategoryId, String subCategoryName, Category category, List<ProductResponse> products) {
		super();
		this.subCategoryId = subCategoryId;
		this.subCategoryName = subCategoryName;
		this.category = category;
		this.products = products;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<ProductResponse> getProducts() {
		return products;
	}

	public void setProducts(List<ProductResponse> products) {
		this.products = products;
	}
}
