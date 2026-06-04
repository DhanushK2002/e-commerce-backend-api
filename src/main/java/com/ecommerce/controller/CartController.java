package com.ecommerce.controller;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.dto.CartResponse;
import com.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addItemsToCart(@RequestBody CartItemRequest cartItemRequest){
        ApiResponse<String> response =  cartService.addItemsToCart(cartItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/view")
    public ResponseEntity<ApiResponse<CartResponse>> viewCart(){
        ApiResponse<CartResponse> response = cartService.viewCart();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<String>> clearCart(){
        ApiResponse<String> response = cartService.clearCart();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{itemId}")
    public ResponseEntity<ApiResponse<String>> updateCartByItemId(@PathVariable Long itemId, @RequestParam Integer quantity){
        ApiResponse<String> response = cartService.updateCartByItemId(itemId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<ApiResponse<String>> deleteCartItemById(@PathVariable Long itemId){
        ApiResponse<String> response = cartService.deleteCartItemById(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
