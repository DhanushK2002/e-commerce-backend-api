package com.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {

	private Long productId;
	
	@NotNull(message = "Product name cannot be blank")
	private String productName;
	
	@NotNull(message = "Product price must be >0")
	@Min(1)
	private Double price;
	
	@NotNull(message = "Stock must be >0")
	@Min(1)
	private Integer stock;
	
	@NotNull(message = "Product description cannot be blank")
	private String description;
	
	private Long subCategoryId;
	private Long categoryId;

	public ProductRequest() {
		super();
	}

	public ProductRequest(Long productId, String productName, Double price, Long subCategoryId, Integer stock, String description, Long categoryId) {
		super();
		this.productName = productName;
		this.price = price;
		this.subCategoryId = subCategoryId;
		this.stock = stock;
		this.description = description;
		this.productId = productId;
		this.categoryId = categoryId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategory(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategory(Long categoryId) {
		this.categoryId = categoryId;
	}
}
