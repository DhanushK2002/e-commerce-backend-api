package com.ecommerce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Product;

public interface ProductService {
	
	public List<Product> getAllProducts();
	
	public ProductDto addProduct(ProductDto productDto, String username);
	
	public ProductDto getProductById(Long productId);

	public ProductDto updateProduct(Long productId, ProductDto productDto);
	
	public void deleteProduct(Long productId);
	
	public String placeOrder(Long userId, Long productId, Integer quantity);

	public List<OrderDto> getMyOrders();
	
	public ResponseEntity<?> getAllOrders();

}
