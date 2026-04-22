package com.ecommerce.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prodId;
	
	private String prodName;
	
	private Long price;
	
	private String productCategory;
	
	public Product() {
		super();
	}
	
	public Product(Long prodId, String prodName, Long price, String productCategory) {
		super();
		this.prodId = prodId;
		this.prodName = prodName;
		this.price = price;
		this.productCategory = productCategory;
	}

	public Long getId() {
		return prodId;
	}

	public void setId(Long prodId) {
		this.prodId = prodId;
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

	public String getProdCategory() {
		return productCategory;
	}
	
	public void setProdCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	@Override
	public String toString() {
		return "Product [id=" + prodId + ", prodName=" + prodName + ", price=" + price + ",category=" + productCategory + "]";
	}
	
	
	
	
}
