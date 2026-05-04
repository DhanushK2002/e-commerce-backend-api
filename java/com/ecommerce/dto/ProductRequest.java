package com.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {

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
	
	private String productCategory;

	public ProductRequest() {
		super();
	}

	public ProductRequest(Long productId, String productName, Double price, String productCategory, Integer stock, String description) {
		super();
		this.productName = productName;
		this.price = price;
		this.productCategory = productCategory;
		this.stock = stock;
		this.description = description;
		this.productId = productId;
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

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	@Override
	public String toString() {
		return "ProductRequest [productName=" + productName + ", price=" + price + ", stock=" + stock + ", description="
				+ description + ", productCategory=" + productCategory + "]";
	}
}
