package com.ecommerce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.OrderRespone;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.model.Product;

public interface ProductService {
	
	public ApiResponse<List<ProductResponse>> getAllProducts();
	
	public ApiResponse<Void> addProduct(ProductRequest productRequest, String username);
	
	public ApiResponse<ProductResponse> updateProduct(Long productId, ProductRequest request);
	
	public ApiResponse<Void> deleteProduct(Long productId);
	
	public ApiResponse<ProductResponse> getProductById(Long productId);
	

	
	
	public String placeOrder(Long userId, Long productId, Integer quantity);

	public List<OrderRespone> getMyOrders();
	
	public ResponseEntity<?> getAllOrders();

}
