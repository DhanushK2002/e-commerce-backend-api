package com.ecommerce.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.dto.*;
import com.ecommerce.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
@Slf4j
@AllArgsConstructor
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapperModel;
    private final OrderRepository orderRepo;
//	private static final Logger log = LoggerFactory.getLogger(ProductServiceImplementation.class);

    // List of All Products
    @Override
    public ApiResponse<List<ProductResponse>> getAllProducts() {

        List<Product> products = productRepo.findAll();
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Database is empty");
        }

        List<ProductResponse> productsResponse = products.stream()
                .map(product -> mapperModel.map(product, ProductResponse.class))
                .toList();

        return new ApiResponse<List<ProductResponse>>(true, "List of Products", productsResponse, LocalDateTime.now(), ResponseEntity.status(HttpStatus.FOUND));
    }

    // Add New Product
    @Override
    public ApiResponse<Void> addProduct(ProductRequest productRequest, String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new CustomException("User is not ADMIN", HttpStatus.FORBIDDEN));

        log.info("User is = {}", user.getUsername());

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ADMIN") || role.getName().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new CustomException("You are not authorised", HttpStatus.FORBIDDEN);
        }

        Product product = mapperModel.map(productRequest, Product.class);
        productRepo.save(product);
        return new ApiResponse<Void>(true, "Product Saved Successfully", LocalDateTime.now(), ResponseEntity.status(HttpStatus.CREATED));
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
                LocalDateTime.now(), ResponseEntity.status(HttpStatus.valueOf(200)));
    }

    // Delete Product By ID
    @Override
    public ApiResponse<Void> deleteProduct(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Id Not Found"));

        productRepo.delete(product);

        return new ApiResponse<Void>(true, "Product Deleted Successfully", LocalDateTime.now(), ResponseEntity.status(HttpStatus.OK));
    }

    // Find Product By ID
    @Override
    public ApiResponse<ProductResponse> getProductById(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        ProductResponse productResponse = mapperModel.map(product, ProductResponse.class);

        return new ApiResponse<ProductResponse>(true, "Product Found!", productResponse, LocalDateTime.now(), ResponseEntity.status(HttpStatus.FOUND));
    }

    // Place order
    @Transactional
    @Override
    public String placeOrder(OrderRequest orderRequest) {
        Product product = productRepo.findById(orderRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (orderRequest.getQuantity() > product.getStock()) {
            throw new RuntimeException("Not enough stock, available stock is " + product.getStock());
        }
        product.setStock(product.getStock() - orderRequest.getQuantity());
        productRepo.save(product);

        User user = userRepo.findById(orderRequest.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        Order newOrder = new Order(user, product, orderRequest.getQuantity());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (user.getUsername().equals(username))
            orderRepo.save(newOrder);
        else
            throw new RuntimeException("ERROR");

        return "Order Placed for the item " + product.getProductName();
    }

    // Orders of the respected Users
    @Override
    public ApiResponse<List<OrderResponse>> getMyOrders() {
        String identifier = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(identifier)
                .orElseThrow(() -> new UserNotFoundException("User not found with username " + identifier));
        List<Order> orders = orderRepo.findByUser_UserId(user.getUserId());

        List<OrderResponse> orderResponse = orders.stream()
                .map(OrderResponse::new)//.map(order -> new OrderResponse(order))
                .toList();
        return new ApiResponse<List<OrderResponse>>(true, "Your orders", orderResponse, LocalDateTime.now(), ResponseEntity.status(HttpStatus.OK));
    }

    // All orders
    @Override
    public ApiResponse<List<OrderResponse>> getAllOrders() {

        List<OrderResponse> orders = orderRepo.findAll()
                .stream()
                .map(order -> new OrderResponse(order))
                .collect(Collectors.toList());

        return new ApiResponse<List<OrderResponse>>(true, "All customer orders", orders, LocalDateTime.now(), ResponseEntity.status(HttpStatus.FOUND));
    }
}
