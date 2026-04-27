package com.ecommerce.service;

import java.util.List;


import com.ecommerce.dto.ProductRequest;
import com.ecommerce.model.Product;

public interface ProductService {
	
	public List<Product> getAllProducts();
	
	public ProductRequest addProduct(ProductRequest productDto);
	
	public ProductRequest getProductById(Long productId);

	public ProductRequest updateProduct(Long productId, ProductRequest productDto);
	
	public void deleteProduct(Long productId);
	
	public List<ProductRequest> getProductByCategory(String productCategory);
	
	public String placeOrder(Integer quantity, Long userId, Long productId);
}
