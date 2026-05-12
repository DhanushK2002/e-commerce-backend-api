package com.ecommerce.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
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

	private final ProductRepository productRepo;

	
	private final UserRepository userRepo;

	
	private final ModelMapper mapperModel;

	
	private final OrderRepository orderRepo;
	

	public ProductServiceImplementation(ProductRepository productRepo, UserRepository userRepo, ModelMapper mapperModel,
			OrderRepository orderRepo) {
		super();
		this.productRepo = productRepo;
		this.userRepo = userRepo;
		this.mapperModel = mapperModel;
		this.orderRepo = orderRepo;
	}

	// List of All Products
	@Override
	public ApiResponse<List<ProductResponse>> getAllProducts() {

		List<Product> products = productRepo.findAll();
		if (products == null)
			throw new ResourceNotFoundException("Database is empty");

		List<ProductResponse> productsResponse = products.stream()
				.map(product -> mapperModel.map(product, ProductResponse.class)).toList();
		
		return new ApiResponse<List<ProductResponse>>(true, "List of Products", productsResponse, LocalDateTime.now());
	}

	// Add New Product 
	@Override
	public ApiResponse<Void> addProduct(ProductRequest productRequest, String username) {
		User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User is not ADMIN"));

		System.out.println("User is " + user.getUsername());

		boolean isAdmin = user.getRoles().stream()
				.anyMatch(role -> role.getName().equals("ADMIN") || role.getName().equals("ROLE_ADMIN"));

		if (!isAdmin) {
			throw new UserNotFoundException("You are not authorised");
		}

		Product product = mapperModel.map(productRequest, Product.class);
		productRepo.save(product);
		return new ApiResponse<Void>(true, "Product Saved Successfully", LocalDateTime.now());
	}

	// Update Product By ID 
	@Override
	public ApiResponse<ProductResponse> updateProduct(Long productId, ProductRequest request) {
		Product products = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		products.setProductName(request.getProductName());
		products.setPrice(request.getPrice());
		products.setDescription(request.getDescription());
		products.setStock(request.getStock());

		ProductResponse productResponse = mapperModel.map(productRepo.save(products), ProductResponse.class);

		return new ApiResponse<ProductResponse>(true, "Product Updated Successfully", productResponse,
				LocalDateTime.now());
	}

	// Delete Product By ID
	@Override
	public ApiResponse<Void> deleteProduct(Long productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product Id Not Found"));

		productRepo.delete(product);

		return new ApiResponse<Void>(true, "Product Deleted Successfully", LocalDateTime.now());
	}

	// Find Product By ID
	@Override
	public ApiResponse<ProductResponse> getProductById(Long productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		ProductResponse productResponse = mapperModel.map(product, ProductResponse.class);

		return new ApiResponse<ProductResponse>(true, "Product Found!", productResponse, LocalDateTime.now());
	}

	// Place order
	@Transactional
	@Override
	public String placeOrder(Long userId, Long productId, Integer quantity) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		if (product.getStock() <= 0) {
			throw new RuntimeException("Out of stock, available stock is " + product.getStock());
		}
		product.setStock(product.getStock() - quantity);
		productRepo.save(product);

		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		Order newOrder = new Order(user, product, quantity);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		if (user.getUsername().equals(username))
			orderRepo.save(newOrder);
		else
			throw new RuntimeException("ERROR");

		return "Order Placed for the item " + product.getProductName();
	}

	// Orders of the respected Users
	@Override
	public List<OrderResponse> getMyOrders() {
		String identifier = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.findByUsername(identifier)
				.orElseThrow(() -> new UserNotFoundException("User not found with username " + identifier));
		List<Order> orders = orderRepo.findByUser_UserId(user.getUserId());
//		List<OrderDto> ordersDto = orders.stream()
//				.map(order -> mapperModel.map(order, OrderDto.class))
//				.toList();
//		
//		return ordersDto;
//		return orders.stream().map(OrderResponse::new) // .map(order -> mapperModel(order, OrderDto.class)
//				.toList();
		return null;
	}

//	public List<Order> getMyOrders() {
//		String identifier = SecurityContextHolder.getContext().getAuthentication().getName();
//		User user = userRepo.findByEmail(identifier)
//				.orElseThrow(() -> new RuntimeException("User not found with email "+identifier));
//		return orderRepo.findByUser_UserId(user.getUserId());
//	}

	// All orders
	@Override
	public ApiResponse<List<OrderResponse>> getAllOrders() {
		
		List<OrderResponse> orders = orderRepo.findAll()
				.stream()
				.map(order -> mapperModel.map(order, OrderResponse.class)) 																					// mapperModel(order,OrderDto.class))
				.collect(Collectors.toList());

		return new ApiResponse<List<OrderResponse>>(true, "All customer orders",orders, LocalDateTime.now());
	}
}
