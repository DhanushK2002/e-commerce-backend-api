package com.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

	@NotNull(message = "Product name is required")
	private String prodName;

	@NotNull(message = "Product price is required")
	private Long price;
	
	private String productCategory;

	public ProductRequest() {
		super();
	}

	public ProductRequest(String prodName, Long price, String productCategory) {
		super();
		this.prodName = prodName;
		this.price = price;
		this.productCategory = productCategory;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}
	
	public String getCategory() {
		return productCategory;
	}

	public void setCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	@Override
	public String toString() {
		return "ProductDto [prodName=" + prodName + ", price=" + price + ", category="+productCategory + "]";
	}

}
