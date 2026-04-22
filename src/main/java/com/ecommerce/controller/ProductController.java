package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ProductRequest;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;
	
	// List of All Products
	@GetMapping("/products")
	public List<Product> getAllProducts() {
		return service.getAllProducts();
	}
	
	//Add New Product
	@PostMapping("/products")
	public void addProduct(@RequestBody Product product){
		service.addProduct(product);
	}
	
	//Find Product By ID
	@GetMapping("/products/{prodId}")
	public ResponseEntity<?> getProductById(@PathVariable Long prodId){
		return service.getProductById(prodId);
	}
	
	//Update Product By ID
	@PutMapping("/products/{prodId}")
	public String updateProduct(@PathVariable Long prodId,@RequestBody ProductRequest product) {
		service.updateProduct(prodId, product);
		return "Product Updated Successfully";
	}
	
	//Find Products By Category 
	@GetMapping("/products/search/{productCategory}")
	public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String productCategory){
		System.out.println("Searching for category "+productCategory);
		List<Product> products = service.getProductByCategory(productCategory);
		if(products.isEmpty())
			return new  ResponseEntity("Specified Category Not Foudnd",HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(products,HttpStatus.OK);
		
	}
	
	//Delete Product By ID
	@DeleteMapping("/products/{prodId}")
	public void deleteProduct(@PathVariable Long prodId) {
		service.deleteProduct(prodId);
	}
}
