package com.ecommerce.dto;

import com.ecommerce.model.Category;
import com.ecommerce.model.SubCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	private Long productId;
	private String productName;
	private Double price;
	private Integer stock;
	private String description;
	@JsonIgnore
	private SubCategory subCategory;
	@JsonIgnore
	private Category category;
}
