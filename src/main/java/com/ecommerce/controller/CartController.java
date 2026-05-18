package com.ecommerce.controller;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CartItemDto;
import com.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("cart/add")
    public ApiResponse<String> addItemsToCart(@RequestBody CartItemDto cartItemDto){
        return cartService.addItemsToCart(cartItemDto);
    }



}
