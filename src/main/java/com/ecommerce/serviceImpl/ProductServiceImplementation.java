package com.ecommerce.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

//import org.modelmapper.ModelMapper;
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

//	@Autowired
//	private ModelMapper mapperModel;

	@Autowired
	private OrderRepository orderRepo;

	private ProductRequest convertToDto(Product product) {
		ProductRequest dto = new ProductRequest();
		dto.setProductId(product.getProductId());
		dto.setProductName(product.getProductName());
		dto.setPrice(product.getPrice());
		dto.setDescription(product.getDescription());
		dto.setProductCategory(product.getProductCategory());
		dto.setStock(product.getStock());
		return dto;
	}

	private Product convertToEntity(ProductRequest dto) {
		Product product = new Product();
		product.setProductId(dto.getProductId());
		product.setProductName(dto.getProductName());
		product.setPrice(dto.getPrice());
		product.setDescription(dto.getDescription());
		product.setProductCategory(dto.getProductCategory());
		product.setStock(dto.getStock());
		return product;
	}

	// List of All Products
	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	// Add New Product
	@Override
	public ProductRequest addProduct(ProductRequest productDto) {
		// Product product = mapperModel.map(productDto, Product.class);
		// productRepo.save(product);
		Product product = convertToEntity(productDto);
		return convertToDto(productRepo.save(product));
	}

	// Find Product By ID
	@Override
	public ProductRequest getProductById(Long productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		return convertToDto(product);
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
		return convertToDto(productRepo.save(products));
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
				.map(product -> this.convertToDto(product)) // .map(this::convertToDto)
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
		orderRepo.save(newOrder);

		return "Order Placed for the item " + product.getProductName();
	}

	
	// Orders of the respected Users
	@Override
	public List<OrderResponseRequest> getMyOrders() {
		String identifier = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.findByEmail(identifier)
				.orElseThrow(() -> new UserNotFoundException("User not found with email "+identifier));
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
