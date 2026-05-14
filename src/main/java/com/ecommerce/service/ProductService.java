package com.ecommerce.service;

import java.util.List;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;

public interface ProductService {
	
	public ApiResponse<List<ProductResponse>> getAllProducts();
	
	public ApiResponse<Void> addProduct(ProductRequest productRequest, String username);
	
	public ApiResponse<ProductResponse> updateProduct(Long productId, ProductRequest request);
	
	public ApiResponse<Void> deleteProduct(Long productId);
	
	public ApiResponse<ProductResponse> getProductById(Long productId);
	
	public String placeOrder(Long userId, Long productId, Integer quantity);

	public ApiResponse<List<OrderResponse>> getMyOrders();
	
	public ApiResponse<List<OrderResponse>> getAllOrders();

}
