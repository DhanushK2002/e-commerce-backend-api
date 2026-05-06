package com.ecommerce.dto;

import com.ecommerce.model.Category;
import com.ecommerce.model.SubCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductDto {

	private Long productId;
	
	@NotNull(message = "Product name is required")
	private String productName;

	@NotNull(message = "Product price is required")
	@Min(1)
	private Double price;
	
	@NotNull(message = "Min Stock should be added")
	@Min(1)
	private Integer stock;
	
	@NotNull(message = "Fill up the product description")
	private String description;
	
	@JsonIgnore
	private SubCategory subCategory;

	@JsonIgnore
	private Category category;

	public ProductDto() {
		super();
	}

	public ProductDto(Long productId, String productName, Double price, Integer stock, String description, SubCategory subCategory, Category category) {
		super();
		this.productName = productName;
		this.price = price;
		this.stock = stock;
		this.description = description;
		this.productId = productId;
		this.subCategory = subCategory;
		this.category = category;
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

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
