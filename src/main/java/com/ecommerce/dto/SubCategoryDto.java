package com.ecommerce.dto;

import java.util.List;

import com.ecommerce.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.NIP;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryDto {
	private Long subCategoryId;
	private String subCategoryName;
	@JsonIgnore
	private Category category;
	private List<ProductResponse> products;
}
