package com.ecommerce.serviceImpl;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.dto.CartItemResponse;
import com.ecommerce.dto.CartResponse;
import com.ecommerce.exception.CustomException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepo;
    private final CartRepository cartRepo;
    private final UserRepository userRepo;
    private final CartItemRepository cartItemRepo;

    //HELPER Method
    private User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }


    // ADD items to the cart
    @Override
    public ApiResponse<String> addItemsToCart(CartItemRequest cartItemRequest) {
//        String username = getCurrentUser();// SecurityContextHolder.getContext().getAuthentication().getName();

//        User user = userRepo.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User user = getCurrentUser();

        Product product = productRepo.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new CustomException("Cart not found", HttpStatus.NOT_FOUND));
//                .orElseGet(() -> {
//                    Cart newCart = new Cart();
//                    newCart.setUser(user);
//                    return cartRepo.save(newCart);
//                });

        if(cartItemRequest.getQuantity() > product.getStock())
            throw new CustomException("Quantity Out of stock", HttpStatus.BAD_REQUEST);

        Optional<CartItem> existingItemOpt = cart.getCartItemList().stream()
                .filter(item -> item.getProduct().getProductId().equals(cartItemRequest.getProductId()))
                .findFirst();

        if(existingItemOpt.isPresent()){
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + cartItemRequest.getQuantity());

            cartItemRepo.save(existingItem);
        }else{
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(cartItemRequest.getQuantity());

            cartItemRepo.save(newCartItem);
        }
        return new ApiResponse<>(
                true,
                "Item added successfully",
                null,
                LocalDateTime.now(),
                201
        );
    }

    // VIEW cart
    @Override
    public ApiResponse<CartResponse> viewCart() {
//        String username = getCurrentUser();// SecurityContextHolder.getContext().getAuthentication().getName();

//        User user = userRepo.findByUsername(username)
//                .orElseThrow(()-> new UserNotFoundException("User not found"));

        User user = getCurrentUser();

        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepo.save(newCart);
                });
//                .orElseThrow(() -> new CustomException("Cart not found", HttpStatus.NOT_FOUND));

        List<CartItemResponse> itemResponses = cart.getCartItemList().stream()
                .map(item->{
                    CartItemResponse response = new CartItemResponse();
                    response.setCartItemId(item.getCartItemId());
                    response.setQuantity(item.getQuantity());
                    response.setProductId(item.getProduct().getProductId());
                    response.setProductName(item.getProduct().getProductName());
                    response.setProductPrice(item.getProduct().getPrice());
                    response.setSubTotal(item.getQuantity() * item.getProduct().getPrice());
                    return response;
                }).toList();

        Double totalPrice = itemResponses.stream()
                .mapToDouble(CartItemResponse::getSubTotal/*itemPrice -> itemPrice.getSubTotal()*/)
                .sum();
        CartResponse cartResponse = new CartResponse(cart.getCartId(), itemResponses, totalPrice);

//        if(itemResponses.isEmpty())
//            throw new CustomException("Your cart is empty", HttpStatus.FOUND);

        return new ApiResponse<>(
                true,
                "Cart fetched successfully",
                cartResponse,
                LocalDateTime.now(),
                200
        );
    }

    // CLEAR cart
    @Override
    @Transactional
    public ApiResponse<String> clearCart() {
//        String username = getCurrentUser();// SecurityContextHolder.getContext().getAuthentication().getName();

//        User user = userRepo.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User user = getCurrentUser();

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new CustomException("Cart not found", HttpStatus.NOT_FOUND));

//        cartItemRepo.deleteAll(cart.getCartItemList());

        cart.getCartItemList().clear();

        cartRepo.save(cart);

        return new ApiResponse<>(
                true,
                "Cart Items cleared successfully",
                null,
                LocalDateTime.now(),
                200
        );
    }

    // UPDATE cart by ITEM ID
    @Override
    public ApiResponse<String> updateCartByItemId(Long itemId, Integer quantity) {
//        String username = getCurrentUser();// SecurityContextHolder.getContext().getAuthentication().getName();

//        User user = userRepo.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User user = getCurrentUser();

        CartItem cartItem = cartItemRepo.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        if(!cartItem.getCart().getUser().getUserId().equals(user.getUserId()))
            throw new CustomException("You are not authorised to update", HttpStatus.UNAUTHORIZED);

        if(quantity > cartItem.getProduct().getStock())
            throw new CustomException("Available stock is "+cartItem.getProduct().getStock(), HttpStatus.BAD_REQUEST);

        cartItem.setQuantity(quantity);
        cartItemRepo.save(cartItem);
        return new ApiResponse<>(
                true,
                "Cart item updated successfully",
                null,
                LocalDateTime.now(),
                200
        );
    }

    // DELETE Specific item from cart
    @Override
    public ApiResponse<String> deleteCartItemById(Long itemId) {
//        String username =  getCurrentUser();//SecurityContextHolder.getContext().getAuthentication().getName();

//        User user = userRepo.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User user = getCurrentUser();

        CartItem cartItem = cartItemRepo.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        if(!cartItem.getCart().getUser().getUserId().equals(user.getUserId()))
            throw new CustomException("You are not authorised", HttpStatus.UNAUTHORIZED);

        cartItemRepo.deleteById(itemId);
        return new ApiResponse<>(
                true,
                "Item "+cartItem.getProduct().getProductName()+" deleted from cart successfully",
                null,
                LocalDateTime.now(),
                200
        );
    }
}
