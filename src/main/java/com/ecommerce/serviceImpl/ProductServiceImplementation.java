package com.ecommerce.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.OrderResponseRequest;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UserNotFoundException;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.ProductService;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper mapperModel;

	@Autowired
	private OrderRepository orderRepo;

//	private ProductRequest convertToDto(Product product) {
//		ProductRequest productDto = new ProductRequest();
//		productDto.setProductId(product.getProductId());
//		productDto.setProductName(product.getProductName());
//		productDto.setPrice(product.getPrice());
//		productDto.setDescription(product.getDescription());
//		productDto.setProductCategory(product.getProductCategory());
//		productDto.setStock(product.getStock());
//		return productDto;
//	}

//	private Product convertToEntity(ProductRequest productDto) {
//		Product product = new Product();
//		product.setProductId(productDto.getProductId());
//		product.setProductName(productDto.getProductName());
//		product.setPrice(productDto.getPrice());
//		product.setDescription(productDto.getDescription());
//		product.setProductCategory(productDto.getProductCategory());
//		product.setStock(productDto.getStock());
//		return product;
//	}

	// List of All Products
	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	// Add New Product
	@Override
	public ProductRequest addProduct(ProductRequest productDto) {
		 Product product = mapperModel.map(productDto, Product.class);
		 Product savedProduct = productRepo.save(product);
		 
		 ProductRequest productRequest = mapperModel.map(savedProduct, ProductRequest.class);
		 return productRequest;
//		Product product = convertToEntity(productDto);
//		return convertToDto(productRepo.save(product));
	}

	// Find Product By ID
	@Override
	public ProductRequest getProductById(Long productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		return mapperModel.map(product, ProductRequest.class);
		//return convertToDto(product);
	}

	// Update Product By ID
	@Override
	public ProductRequest updateProduct(Long productId, ProductRequest productDto) {
		Product products = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		products.setProductName(productDto.getProductName());
		products.setPrice(productDto.getPrice());
		products.setDescription(productDto.getDescription());
		products.setProductCategory(productDto.getProductCategory());
		products.setStock(productDto.getStock());
		
		return mapperModel.map(productRepo.save(products), ProductRequest.class);
		
		//return convertToDto(productRepo.save(products));
	}

	// Delete Product By ID
	@Override
	public void deleteProduct(Long productId) {
		productRepo.deleteById(productId);
	}

	// Find Products By Category
	@Override
	public List<ProductRequest> getProductByCategory(String productCategory) {
		return productRepo.getProductByCategory(productCategory)
				.stream()
				.map(product -> this.mapperModel.map(product, ProductRequest.class)) // .map(this::convertToDto)
				//.map(product -> this.convertToDto(product)) 
				.collect(Collectors.toList()); // .toList();																																																													
	}

	// Place order
	@Transactional
	@Override
	public String placeOrder(Integer quantity, Long userId, Long productId) {
		Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		if (product.getStock() <= 0) {
			throw new RuntimeException("Out of stock, available stock is " + product.getStock());
		}
		product.setStock(product.getStock() - quantity);
		productRepo.save(product);

		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		Order newOrder = new Order(user, product, quantity);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(user.getUsername().equals(username))
			orderRepo.save(newOrder);
		else
			throw new RuntimeException("Not the same user");
//
		return "Order Placed for the item " + product.getProductName();
	}

	
	// Orders of the respected Users
	@Override
	public List<OrderResponseRequest> getMyOrders() {
		String identifier = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(identifier);
		User user = userRepo.findByUsername(identifier)
				.orElseThrow(() -> new UserNotFoundException("User not found with username "+identifier));
		List<Order> orders = orderRepo.findByUser_UserId(user.getUserId());
		
		return orders.stream()
				.map(OrderResponseRequest :: new)
				.toList();
	}

//	public List<Order> getMyOrders() {
//		String identifier = SecurityContextHolder.getContext().getAuthentication().getName();
//		User user = userRepo.findByEmail(identifier)
//				.orElseThrow(() -> new RuntimeException("User not found with email "+identifier));
//		return orderRepo.findByUser_UserId(user.getUserId());
//	}
	
	
	@Override
	public ResponseEntity<List<OrderResponseRequest>> getAllOrders() {
		List<OrderResponseRequest> orders =  orderRepo.findAll()
				.stream()
				.map(OrderResponseRequest :: new) //.map(order -> new OrderRepsonseRequest(order))
				.collect(Collectors.toList());
		return ResponseEntity.ok(orders);
	}	
}
