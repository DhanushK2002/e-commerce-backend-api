package com.ecommerce.service;

import java.util.List;

import com.ecommerce.dto.*;
import org.springframework.data.domain.Pageable;

public interface ProductService {
	
	public ApiResponse<PageResponse<ProductResponse>> getAllProducts(Pageable pageable);
	
	public ApiResponse<Void> addProduct(ProductRequest productRequest, String username);
	
	public ApiResponse<ProductResponse> updateProduct(Long productId, ProductRequest request);
	
	public ApiResponse<Void> deleteProduct(Long productId);
	
	public ApiResponse<ProductResponse> getProductById(Long productId);
	
	public ApiResponse<String> placeOrder(OrderRequest orderRequest);

	public ApiResponse<List<OrderResponse>> getMyOrders();
	
	public ApiResponse<List<OrderResponse>> getAllOrders();

}
