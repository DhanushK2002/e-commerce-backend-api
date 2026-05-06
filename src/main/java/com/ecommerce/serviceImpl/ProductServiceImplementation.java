package com.ecommerce.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.dto.ProductDto;
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

	// List of All Products
	@Override
	public List<ProductDto> getAllProducts() {
		List<Product> products = productRepo.findAll();
		List<ProductDto> productsDto = products.stream()
				.map(product -> mapperModel.map(product, ProductDto.class))
				.toList();
		return productsDto;
	}

	// Add New Product
	@Override
	public ProductDto addProduct(ProductDto productDto, String username) {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User is not ADMIN"));
		System.out.println("User is " +user.getUsername());
		
		boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIM") || role.getName().equals("ROLE_ADMIN"));
		
		if(!isAdmin) {
			throw new RuntimeException("You are not authorised");
		}
		
		Product product = mapperModel.map(productDto, Product.class);
		Product savedProduct = productRepo.save(product);
		 
		ProductDto productRequest = mapperModel.map(savedProduct, ProductDto.class);
		return productRequest;
	}

	// Find Product By ID
	@Override
	public ProductDto getProductById(Long productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		return mapperModel.map(product, ProductDto.class);
	}

	// Update Product By ID
	@Override
	public ProductDto updateProduct(Long productId, ProductDto productDto) {
		Product products = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		products.setProductName(productDto.getProductName());
		products.setPrice(productDto.getPrice());
		products.setDescription(productDto.getDescription());
		
		products.setStock(productDto.getStock());
		
		return mapperModel.map(productRepo.save(products), ProductDto.class);
	}

	// Delete Product By ID
	@Override
	public void deleteProduct(Long productId) {
		productRepo.deleteById(productId);
	}

	// Place order
	@Transactional
	@Override
	public String placeOrder(Long userId, Long productId, Integer quantity) {
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

		return "Order Placed for the item " + product.getProductName();
	}
	
	// Orders of the respected Users
	@Override
	public List<OrderDto> getMyOrders() {
		String identifier = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.findByUsername(identifier)
				.orElseThrow(() -> new UserNotFoundException("User not found with username "+identifier));
		List<Order> orders = orderRepo.findByUser_UserId(user.getUserId());
//		List<OrderDto> ordersDto = orders.stream()
//				.map(order -> mapperModel.map(order, OrderDto.class))
//				.toList();
//		
//		return ordersDto;
		return orders.stream()
				.map(OrderDto :: new) // .map(order -> mapperModel(order, OrderDto.class)
				.toList();
	}

//	public List<Order> getMyOrders() {
//		String identifier = SecurityContextHolder.getContext().getAuthentication().getName();
//		User user = userRepo.findByEmail(identifier)
//				.orElseThrow(() -> new RuntimeException("User not found with email "+identifier));
//		return orderRepo.findByUser_UserId(user.getUserId());
//	}
	
	
	// All orders
	@Override
	public ResponseEntity<List<OrderDto>> getAllOrders() {
		
		List<OrderDto> orders = orderRepo.findAll()
				.stream()
				.map(OrderDto :: new) //.map(order -> new OrderRepsonseRequest(order)) // .map(order -> mapperModel(order,OrderDto.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok(orders);
	}
}
