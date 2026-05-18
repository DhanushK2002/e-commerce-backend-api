package com.ecommerce.service;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CartItemDto;

public interface CartService {
    public ApiResponse<String> addItemsToCart(CartItemDto cartItemDto);

}
