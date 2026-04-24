package com.ecommerce.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ModelMapper mapperModel;
	
	@Autowired
	private OrderRepository orderRepo;

	// List of All Products
	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}
	
	//Add New Product
	@Override
	public void addProduct(ProductDto productDto) {
		Product product = mapperModel.map(productDto, Product.class);
		productRepo.save(product);
	}
	
	//Find Product By ID
	@Override
	public ResponseEntity<?> getProductById(Long productId){
		Optional<Product> product = productRepo.findById(productId);
		
		if(product.isPresent())
			return ResponseEntity.ok(product.get());
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
	}

	//Update Product By ID
	@Override
	public Product updateProduct(Long productId, ProductDto productDto) {
		Product products = productRepo.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product id not found to update"));
		
		products.setProductName(productDto.getProductName());
		products.setPrice(productDto.getPrice());
		products.setDescription(productDto.getDescription());
		products.setProductCategory(productDto.getProductCategory());
		products.setStock(productDto.getStock());
		return productRepo.save(products);
	}
	
	//Delete Product By ID
	@Override
	public void deleteProduct(Long productId) {
		productRepo.deleteById(productId);
	}

	//Find Products By Category
	@Override
	public List<Product> getProductByCategory(String productCategory) {
		return productRepo.getProductByCategory(productCategory);
	}

	//Place order
	@Transactional
	@Override
	public String placeOrder(Integer quantity, Long productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found") );
		if(product.getStock() <= 0) {
			System.out.println(product.getStock());
			throw new RuntimeException("Out of stock, available stock is "+product.getStock());
		}
			product.setStock(product.getStock() - quantity);
			productRepo.save(product);
			
			Order newOrder = new Order(product,quantity);
			orderRepo.save(newOrder);			
			
			return "Order Placed for the item "+product.getProductName();			
	}
}
