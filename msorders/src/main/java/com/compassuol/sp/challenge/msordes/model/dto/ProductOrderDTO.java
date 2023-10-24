package com.compassuol.sp.challenge.msordes.model.dto;

import com.compassuol.sp.challenge.msordes.model.entity.Order;
import com.compassuol.sp.challenge.msordes.model.entity.ProductOrder;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOrderDTO {
    @JsonProperty("product_id")
    private Long productId;
    private Integer quantity;

    public static ProductOrder toModel(ProductOrderDTO dto, Order order) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setProductId(dto.getProductId());
        productOrder.setQuantity(dto.getQuantity());
        productOrder.setOrder(order);
        return productOrder;
    }

    public static ProductOrderDTO toDto(ProductOrder productOrder) {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setProductId(productOrder.getProductId());
        productOrderDTO.setQuantity(productOrder.getQuantity());

        return productOrderDTO;
    }
}


