//package com.ecommerce.serviceImpl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.ecommerce.exception.UserNotFoundException;
//import com.ecommerce.model.Cart;
//import com.ecommerce.model.User;
//import com.ecommerce.repository.CartRepository;
//import com.ecommerce.repository.UserRepository;
//import com.ecommerce.service.CartService;
//
//public class CartServiceImplementation implements CartService {
//
//	@Autowired
//	UserRepository userRepo;
//	
//	@Autowired
//	CartRepository cartRepo;
//	
//	@Override
//	public Cart getCartForUser(Long userId) {
////		User user = userRepo.findById(userId)
////				.orElseThrow(() -> new UserNotFoundException("User not found"));
////	
////		return cartRepo.findByUser(user)
////				.orElseGet(() ->{
////					Cart newCart = new Cart();
////					newCart.setUser(user);
////					catRepo.s
////				});
//		return null;
//	}
//
//	@Override
//	public Cart addToCart(Long userId, Long productId, Integer quantity) {
//		return null;
//	}
//
//	@Override
//	public Cart removeFromCart(Long userId, Long cartItemId) {
//		return null;
//	}
//
//	@Override
//	public void clearCart() {
//	}
//}
