package com.ecommerce.service;

import com.ecommerce.model.Cart;

public interface CartService {
	public Cart getCartForUser(Long userId);
	public Cart addToCart(Long userId, Long productId, Integer quantity);
	public Cart removeFromCart(Long userId, Long cartId);
	public void clearCart();
}
