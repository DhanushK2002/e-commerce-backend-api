package com.ecommerce.serviceImpl;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CartItemDto;
import com.ecommerce.model.CartItem;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


// W I P : WORK IN PROGRESS
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepo;
    private final CartRepository cartRepository;
    private final CartItem cartItem;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse<String> addItemsToCart(CartItemDto cartItemDto) {

        return null;
    }
}
