package com.ecommerce.dto;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @Min(value = 1)
    private Integer quantity;

    private Long userId;

    private Long productId;
}
