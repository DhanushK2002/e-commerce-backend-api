package com.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
