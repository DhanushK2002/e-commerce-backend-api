package com.ecommerce.service;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.dto.CartItemResponse;
import com.ecommerce.dto.CartResponse;

public interface CartService {
    public ApiResponse<String> addItemsToCart(CartItemRequest cartItemRequest);

    public ApiResponse<CartResponse> viewCart();

    public ApiResponse<String> clearCart();

    public ApiResponse<String> updateCartByItemId(Long itemId, Integer quantity);

    public ApiResponse<String> deleteCartItemById(Long itemId);
}
