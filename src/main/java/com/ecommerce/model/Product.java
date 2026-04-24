package com.ecommerce.model;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	private String productName;
	
	private Double price;
	
	private String productCategory;
	
	private String description;
	
	private Integer stock;

	public Product() {
		super();
	}

	public Product(Long productId, String productName, Double price, String productCategory, Integer stock, String description) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.productCategory = productCategory;
		this.stock = stock;
		this.description = description;
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

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", price=" + price
				+ ", productCategory=" + productCategory + ", description=" + description + ", stock=" + stock + "]";
	}

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Order> orders;	
}
