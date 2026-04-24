package com.ecommerce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Product;

public interface ProductService {
	
	public List<Product> getAllProducts();
	
	public void addProduct(ProductDto productDto);
	
	public ResponseEntity<?> getProductById(Long productId);

	public Product updateProduct(Long productId, ProductDto productDto);
	
	public void deleteProduct(Long productId);
	
	public List<Product> getProductByCategory(String productCategory);
	
	public String placeOrder(Integer quantity, Long productId);
}
