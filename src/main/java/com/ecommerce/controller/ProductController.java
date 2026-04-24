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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Product;
import com.ecommerce.serviceImpl.ProductServiceImplementation;

@RestController
public class ProductController {

	@Autowired
	private ProductServiceImplementation service;
	
	// List of All Products
	@GetMapping("/products")
	public List<Product> getAllProducts() {
		return service.getAllProducts();
	}
	
	//Add New Product
	@PostMapping("/products")
	public String addProduct(@RequestBody ProductDto productDto){
		service.addProduct(productDto);
		return "Product Added Successfully";
	}
	
	//Find Product By ID
	@GetMapping("/products/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable Long productId){
		return service.getProductById(productId);
	}
	
	//Update Product By ID
	@PutMapping("/products/{productId}")
	public String updateProduct(@PathVariable Long productId,@RequestBody ProductDto productDto) {
		service.updateProduct(productId, productDto);
		return "Product Updated Successfully";
	}
	
	//Find Products By Category 
	@GetMapping("/products/search/{productCategory}")
	public ResponseEntity<?> getProductByCategory(@PathVariable String productCategory){
		List<Product> products = service.getProductByCategory(productCategory);
		if(products.isEmpty())
			return new  ResponseEntity<>("Specified Category Not Foudnd",HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(products,HttpStatus.OK);		
	}
	
	//Delete Product By ID
	@DeleteMapping("/products/{productId}")
	public String deleteProduct(@PathVariable Long productId) {
		service.deleteProduct(productId);
		return "Product Deleted Successfully";
	}
	
	//Place Order
	@PostMapping("/products/order")
	public String placeOrder(@RequestParam Long productId, @RequestParam Integer quantity) {
		return service.placeOrder(quantity,productId);
	}
}
