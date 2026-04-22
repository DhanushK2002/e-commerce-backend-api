package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ProductRequest;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;


@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	// List of All Products
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}
	
	//Add New Product
	public void addProduct(Product product) {
		productRepo.save(product);
	}
	
	//Find Product By ID
	public ResponseEntity<?> getProductById(Long prodId){
		Optional<Product> product = productRepo.findById(prodId);
		
		if(product.isPresent())
			return ResponseEntity.ok(product.get());
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
	}

	//Update Product By ID
	public Product updateProduct(Long prodId, ProductRequest product) {
		Product products = productRepo.findById(prodId)
				.orElseThrow(() -> new RuntimeException("Product id not found to update"));
		
		products.setProdName(product.getProdName());
		products.setPrice(product.getPrice());
		return productRepo.save(products);
	}
	//Delete Product By ID
	public void deleteProduct(Long prodId) {
		productRepo.deleteById(prodId);
	}

	//Find Products By Category 
	public List<Product> getProductByCategory(String productCategory) {
		return productRepo.getProductByCategory(productCategory);
	}
}
